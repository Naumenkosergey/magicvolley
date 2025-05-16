package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.Util;
import ru.magicvolley.botTelegram.Bot;
import ru.magicvolley.dto.CampUserDto;
import ru.magicvolley.dto.ConfirmReservationDto;
import ru.magicvolley.dto.ReservationDto;
import ru.magicvolley.entity.CampEntity;
import ru.magicvolley.entity.CampUserEntity;
import ru.magicvolley.entity.UserEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampRepository;
import ru.magicvolley.repository.CampUserRepository;
import ru.magicvolley.repository.UserRepository;
import ru.magicvolley.request.AddUserCampRequest;
import ru.magicvolley.response.NotificationResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampUserService {

    private final CampUserRepository campUserRepository;
    private final CampRepository campRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final ProfileCampService profileCampService;
    private final Bot bot;

    @Transactional
    public String makeReservation(ReservationDto reservationDto) {

        String nameResult = reservationDto.username();
        if (reservationDto.userId() == null) {
            CampEntity campEntity = campRepository.findById(reservationDto.campId())
                    .orElseThrow(() -> new EntityNotFoundException(CampEntity.class, reservationDto.campId()));
            String campName = campEntity.getCampName();
            var username = reservationDto.username();
            var telephone = Util.addNotExistChar(reservationDto.telephone(), '+');
            bot.reservedCamp(campName, username, telephone);
        } else {
            createCampUser(reservationDto.campId(), reservationDto.userId(), Boolean.FALSE, 1);
            nameResult = userRepository.findById(reservationDto.userId())
                    .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, reservationDto.userId())).getUsername();
        }
        return nameResult;
    }

    private void createCampUser(UUID campId, UUID userId, boolean isViewed, Integer bookingCount) {
        CampUserEntity campUserEntity = CampUserEntity.builder()
                .id(CampUserEntity.Id.builder()
                        .userId(userId)
                        .campId(campId)
                        .build()
                )
                .bookingConfirmed(Boolean.FALSE)
                .bookingCount(Objects.nonNull(bookingCount) ? bookingCount : 1)
                .isReserved(Boolean.TRUE)
                .isPast(Boolean.FALSE)
                .isViewed(isViewed)
                .build();
        campUserRepository.save(campUserEntity);
    }

    @Transactional
    public Boolean confirmReservation(ConfirmReservationDto confirmReservationRequest) {
        CampUserEntity.Id id = CampUserEntity.Id.builder()
                .campId(confirmReservationRequest.campId())
                .userId(confirmReservationRequest.userId())
                .build();
        CampUserEntity campUserFromDb = campUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CampUserEntity.class, id));
        campUserFromDb.setBookingConfirmed(confirmReservationRequest.isConfirm());
        campUserFromDb.setBookingCount(confirmReservationRequest.count());

        profileCampService.confirmOrUnconfirmCampForUser(confirmReservationRequest);
        campUserFromDb.setIsViewed(Boolean.TRUE);
        return true;
    }

    public List<CampUserDto> getUsersForCampId(UUID campId) {
        List<CampUserEntity> campUserEntities = campUserRepository.findByIdCampId(campId);
        return campUserEntities.stream()
                .map(CampUserDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotifications() {
        Map<UUID, String> campIdToName = campRepository.findAll().stream()
                .collect(Collectors.toMap(CampEntity::getId, CampEntity::getCampName));
        return campUserRepository.findAllNotificationProjection().stream()
                .map(projection -> NotificationResponse.builder()
                        .campId(projection.getCampId())
                        .campName(campIdToName.get(projection.getCampId()))
                        .countNewUsers(projection.getNumberOfUnviewed().intValue())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Integer getCountNewNotifications() {
        return campUserRepository.countByIsViewedFalse();
    }

    @Transactional
    public boolean addUserToCamp(AddUserCampRequest addUserCampRequest) {
        UserEntity userEntity = userRepository.findByTelephone(addUserCampRequest.getTelephone()).orElse(
                authService.addUserForCamp(addUserCampRequest));

        createCampUser(addUserCampRequest.getCampId(), userEntity.getId(), Boolean.TRUE, addUserCampRequest.getBookingCount());
        return true;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recalculateIsViewUsers(UUID campId) {
        if (Util.isAdminCurrentUser()) {
            List<CampUserEntity> allByIdCampId = campUserRepository.findAllByIdCampId(campId);
            allByIdCampId.forEach(x -> x.setIsViewed(Boolean.TRUE));
            campUserRepository.saveAll(allByIdCampId);
        }
    }
}

package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampUserService {

    private final CampUserRepository campUserRepository;
    private final CampRepository campRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Transactional
    public Boolean makeReservation(ReservationDto reservationDto) {

        if (reservationDto.userId() == null) {
            //TODO seng telegram message
        }
        createCampUer(reservationDto.campId(), reservationDto.userId(), Boolean.FALSE);
        return true;
    }

    private void createCampUer(UUID campId, UUID userId,  boolean isViewed) {
        CampUserEntity campUserEntity = CampUserEntity.builder()
                .id(CampUserEntity.Id.builder()
                        .userId(userId)
                        .campId(campId)
                        .build()
                )
                .bookingConfirmed(Boolean.FALSE)
                .bookingCount(1)
                .isReserved(Boolean.TRUE)
                .isPast(Boolean.FALSE)
                .isViewed(Boolean.FALSE)
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
        campUserFromDb.setIsViewed(Boolean.TRUE);
        return true;
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
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
                authService.addUserForCamp(addUserCampRequest.getTelephone()));

        createCampUer(addUserCampRequest.getCampId(), userEntity.getId(), Boolean.TRUE);
        return true;

    }
}

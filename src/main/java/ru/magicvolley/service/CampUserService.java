package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.CampUserDto;
import ru.magicvolley.dto.ReservationDto;
import ru.magicvolley.entity.CampUserEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampUserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampUserService {

    private final CampUserRepository campUserRepository;

    @Transactional
    public Boolean makeReservation(ReservationDto reservationDto) {

        CampUserEntity campUserEntity = CampUserEntity.builder()
                .id(CampUserEntity.Id.builder()
                        .userId(reservationDto.userId())
                        .campId(reservationDto.campId())
                        .build()
                )
                .bookingConfirmed(Boolean.FALSE)
                .isReserved(Boolean.TRUE)
                .isPast(Boolean.FALSE)
                .build();
        campUserRepository.save(campUserEntity);
        return true;
    }

    @Transactional
    public Boolean confirmReservation(ReservationDto reservationDto) {
        CampUserEntity.Id id = CampUserEntity.Id.builder()
                .campId(reservationDto.campId())
                .userId(reservationDto.campId())
                .build();
        CampUserEntity campUserFromDb = campUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CampUserEntity.class, id));
        campUserFromDb.setBookingConfirmed(true);
        return true;
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<CampUserDto> getUsersForCampId(UUID campId) {
        List<CampUserEntity> campUserEntities = campUserRepository.findByIdCampId(campId);
        return campUserEntities.stream()
                .map(CampUserDto::new)
                .collect(Collectors.toList());
    }
}

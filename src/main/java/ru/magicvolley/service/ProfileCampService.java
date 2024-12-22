package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.ConfirmReservationDto;
import ru.magicvolley.entity.ProfileCampsEntity;
import ru.magicvolley.repository.ProfileCampsRepository;

@Service
@RequiredArgsConstructor
public class ProfileCampService {

    private final ProfileCampsRepository profileCampsRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void confirmOrUnconfirmCampForUser(ConfirmReservationDto confirmReservationDto) {

        ProfileCampsEntity.Id id = ProfileCampsEntity.Id.builder()
                .profileId(confirmReservationDto.userId())
                .campId(confirmReservationDto.campId())
                .build();
        profileCampsRepository.findById(id)
                .ifPresentOrElse(profileCampsEntity -> {
                            profileCampsEntity.setIsBooked(confirmReservationDto.isConfirm());
                            profileCampsRepository.save(profileCampsEntity);
                        },
                        () -> {
                            ProfileCampsEntity newProfileCmp = ProfileCampsEntity.builder()
                                    .id(id)
                                    .isBooked(confirmReservationDto.isConfirm())
                                    .isPast(Boolean.FALSE)
                                    .build();
                            profileCampsRepository.save(newProfileCmp);

                        });

    }
}

package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.ProfileDto;
import ru.magicvolley.entity.ProfileCompsEntity;
import ru.magicvolley.entity.ProfileEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.ProfileRepository;
import ru.magicvolley.request.PasswordUpdateForProfile;
import ru.magicvolley.request.ProfileForUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final CoachService coachService;
    private final UserService userService;

    @Transactional
    public List<ProfileDto> getAll() {
        return profileRepository.findAll().stream()
                .map(this::mapProfileEntityToProfileDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public ProfileDto getById(UUID profileId) {
        ProfileEntity profileEntity = profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException(ProfileEntity.class, profileId));
        return mapProfileEntityToProfileDto(profileEntity);
    }

    //
//    public UserEntity create(UserDto user) {
//        return userRepository.save(UserEntity.builder()
//                .id(UUID.randomUUID())
//                .email(user.getEmail())
//                .login(user.getLogin())
//                .build());
//    }
//
    @Transactional
    public ProfileEntity update(ProfileForUpdate profile) {
        ProfileEntity profileEntity = profileRepository.findById(profile.id())
                .orElseThrow(() -> new EntityNotFoundException(ProfileEntity.class, profile.id()));
        profileEntity.setBirthday(profile.birthday());
        profileEntity.setFulName(profile.fulName());
        profileEntity.setTelephone(profile.telephone());
        return profileRepository.save(profileEntity);
    }

    //
//    @Transactional
//    public void delete(UUID id){
//        userRepository.deleteById(id);
//    }
//
//
    private ProfileDto mapProfileEntityToProfileDto(ProfileEntity profile) {
        List<ProfileDto.Camp> pastCamps = new ArrayList<>();
        List<ProfileDto.Camp> nearestCamps = new ArrayList<>();

        profile.getProfileComps().forEach(
                profileCamp -> {
                    if (profileCamp.getIsPast()) {
                        pastCamps.add(creteNewCampForProfile(profileCamp));
                    } else if (profileCamp.getIsBooked()) {
                        nearestCamps.add(creteNewCampForProfile(profileCamp));
                    }
                }
        );

        return new ProfileDto(profile.getFulName(),
                profile.getBirthday(),
                profile.getUser().getEmail(),
                profile.getTelephone(),
                pastCamps,
                nearestCamps
        );
    }

    private ProfileDto.Camp creteNewCampForProfile(ProfileCompsEntity profileCamp) {
        return new ProfileDto.Camp(
                profileCamp.getId().getCampId(),
                profileCamp.getCamp().getCampName(),
                profileCamp.getCamp().getInfo(),
                profileCamp.getCamp().getPrice(),
                profileCamp.getCamp().getDateStart(),
                profileCamp.getCamp().getDateEnd(),
                coachService.getCouches(profileCamp.getCamp().getCoaches()));
    }

    @Transactional
    public ProfileEntity updatePassword(PasswordUpdateForProfile passwordUpdateForProfile) {
        userService.updatePassword(passwordUpdateForProfile);
        return profileRepository.findById(passwordUpdateForProfile.id())
                .orElseThrow(() -> new EntityNotFoundException(ProfileEntity.class, passwordUpdateForProfile.id()));
    }

}

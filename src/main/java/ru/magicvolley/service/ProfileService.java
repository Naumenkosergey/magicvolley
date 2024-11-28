package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.dto.ProfileDto;
import ru.magicvolley.dto.CampDtoForList;
import ru.magicvolley.entity.ProfileCampsEntity;
import ru.magicvolley.entity.ProfileEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.ProfileRepository;
import ru.magicvolley.request.PasswordUpdateForProfile;
import ru.magicvolley.request.ProfileForUpdate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final CoachService coachService;
    private final UserService userService;
    private final CampService campService;
    @Value("${media.prefix.url}")
    private String prefixUrlMedia;

    @Transactional(readOnly = true)
    public List<ProfileDto> getAll() {
        return profileRepository.findAll().stream()
                .map(this::mapProfileEntityToProfileDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public ProfileDto getById(UUID profileId) {
        ProfileEntity profileEntity = profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException(ProfileEntity.class, profileId));
        return mapProfileEntityToProfileDto(profileEntity);
    }

    @Transactional
    public UUID update(ProfileForUpdate profile) {
        ProfileEntity profileEntity = profileRepository.findById(profile.id())
                .orElseThrow(() -> new EntityNotFoundException(ProfileEntity.class, profile.id()));
        profileEntity.setBirthday(profile.birthday());
        profileEntity.setFulName(profile.fulName());
        profileEntity.setTelephone(profile.telephone());
        profileRepository.save(profileEntity);
        return profileEntity.getUserId();
    }

//    @Transactional
//    public void delete(UUID id){
//        userRepository.deleteById(id);
//    }


    private ProfileDto mapProfileEntityToProfileDto(ProfileEntity profile) {

        List<CampDtoForList> pastCamps  = campService.getCampList(profile.getProfileCamps().stream()
                .filter(ProfileCampsEntity::getIsPast)
                .map(ProfileCampsEntity::getCamp)
                .toList());

        List<CampDtoForList> nearestCamps = campService.getCampList(profile.getProfileCamps().stream()
                .filter(ProfileCampsEntity::getIsBooked)
                .map(ProfileCampsEntity::getCamp)
                .toList());

        return new ProfileDto(profile.getFulName(),
                profile.getBirthday(),
                profile.getUser().getEmail(),
                profile.getTelephone(),
                pastCamps,
                nearestCamps,
                new MediaStorageInfo(profile.getAvatar(), prefixUrlMedia)
        );
    }

    @Transactional
    public ProfileEntity updatePassword(PasswordUpdateForProfile passwordUpdateForProfile) {
        userService.updatePassword(passwordUpdateForProfile);
        return profileRepository.findById(passwordUpdateForProfile.id())
                .orElseThrow(() -> new EntityNotFoundException(ProfileEntity.class, passwordUpdateForProfile.id()));
    }

}

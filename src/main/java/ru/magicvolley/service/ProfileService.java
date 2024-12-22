package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.CampDtoForList;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.dto.ProfileDto;
import ru.magicvolley.entity.ProfileCampsEntity;
import ru.magicvolley.entity.ProfileEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.ProfileRepository;
import ru.magicvolley.request.PasswordUpdateForProfile;
import ru.magicvolley.request.ProfileForUpdate;
import ru.magicvolley.response.UserForAdminResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final CoachService coachService;
    private final UserService userService;
    private final CampService campService;
    private final AuthService authService;
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
        profileEntity.setFulName(profile.fullName());
        profileEntity.setTelephone(profile.telephone());
        profileRepository.save(profileEntity);
        return profileEntity.getUserId();
    }


    private ProfileDto mapProfileEntityToProfileDto(ProfileEntity profile) {

        List<CampDtoForList> pastCamps = campService.getCampList(profile.getProfileCamps().stream()
                .filter(ProfileCampsEntity::getIsPast)
                .map(ProfileCampsEntity::getCamp)
                .toList());

        List<CampDtoForList> nearestCamps = campService.getCampList(profile.getProfileCamps().stream()
                .filter(ProfileCampsEntity::getIsBooked)
                .map(ProfileCampsEntity::getCamp)
                .toList());

        List<UserForAdminResponse> usersAll = new ArrayList<>();
        Boolean isAdmin = authService.isAdminCurrentUser();
        if (isAdmin) {
            Map<UUID, MediaStorageInfo> map = profileRepository.findAll().stream()
                    .filter(x -> !x.getUserId().equals(profile.getUserId()))
                    .collect(Collectors.toMap(ProfileEntity::getUserId, v -> new MediaStorageInfo(v.getAvatar(), prefixUrlMedia)));
            usersAll = userService.getAll()
                    .stream()
                    .filter(user -> !user.getId().equals(profile.getUserId()))
                    .map(userDto -> new UserForAdminResponse(userDto, map.get(userDto.getId())))
                    .toList();
        }

        return new ProfileDto(profile.getFulName(),
                profile.getBirthday(),
                profile.getUser().getEmail(),
                profile.getTelephone(),
                pastCamps,
                nearestCamps,
                new MediaStorageInfo(profile.getAvatar(), prefixUrlMedia),
                usersAll,
                isAdmin
        );
    }

    @Transactional
    public ProfileEntity updatePassword(PasswordUpdateForProfile passwordUpdateForProfile) {
        userService.updatePassword(passwordUpdateForProfile);
        return profileRepository.findById(passwordUpdateForProfile.id())
                .orElseThrow(() -> new EntityNotFoundException(ProfileEntity.class, passwordUpdateForProfile.id()));
    }
}

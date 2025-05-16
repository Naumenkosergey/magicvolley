package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.Util;
import ru.magicvolley.dto.UserDto;
import ru.magicvolley.entity.CampUserEntity;
import ru.magicvolley.entity.ProfileEntity;
import ru.magicvolley.entity.RoleEntity;
import ru.magicvolley.entity.UserEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampUserRepository;
import ru.magicvolley.repository.ProfileRepository;
import ru.magicvolley.repository.UserRepository;
import ru.magicvolley.request.AddUserRequest;
import ru.magicvolley.request.PasswordUpdateForProfile;
import ru.magicvolley.response.UserForAdminResponse;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CampUserRepository campUserRepository;

    @Transactional
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserForAdminResponse getById(UUID id) {
        return userRepository.findById(id)
                .map(UserForAdminResponse::new)
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, id));
    }

    @Transactional
    public UUID create(AddUserRequest addUserRequest) {

        RoleEntity roleForRequest = roleService.getRoleForRequest(addUserRequest);
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .telephone(Util.trim(addUserRequest.getTelephone(), '+'))
                .password(passwordEncoder.encode(Util.trim(addUserRequest.getTelephone(),'+')))
                .isBlocked(Objects.nonNull(addUserRequest.getIsBlocked()) ? addUserRequest.getIsBlocked() : false)
                .username(addUserRequest.getUsername())
                .roleId(roleForRequest.getId())
                .role(roleForRequest)
                .build();
        return create(userEntity);
    }

    public UUID create(UserEntity user) {

        userRepository.save(user);
        ProfileEntity profile = ProfileEntity.builder()
                .userId(user.getId())
                .fulName(user.getUsername())
                .telephone(Util.trim(user.getTelephone(), '+'))
                .build();
        profileRepository.save(profile);
        return user.getId();
    }

    @Transactional
    public UUID update(AddUserRequest addUserRequest) {
        UserEntity userFromDb = userRepository.findByTelephone(addUserRequest.getTelephone())
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, addUserRequest.getTelephone()));
        RoleEntity roleForRequest = roleService.getRoleForRequest(addUserRequest);
        userFromDb.setUsername(addUserRequest.getUsername());
        userFromDb.setRoleId(roleForRequest.getId());
        userFromDb.setRole(roleForRequest);
        userFromDb.setIsBlocked(Objects.nonNull(addUserRequest.getIsBlocked()) ? addUserRequest.getIsBlocked() : false);

        return userFromDb.getId();
    }

    @Transactional
    public void delete(UUID id) {
        List<CampUserEntity> allCampUserByUser = campUserRepository.findAllByIdUserId(id);
        if (CollectionUtils.isNotEmpty(allCampUserByUser)) {
            campUserRepository.deleteAll(allCampUserByUser);
        }
        userRepository.deleteById(id);
        profileRepository.deleteById(id);
    }


    public void updatePassword(PasswordUpdateForProfile passwordUpdateForProfile) {
        UserEntity useFromDb = userRepository.findById(passwordUpdateForProfile.id())
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, passwordUpdateForProfile.id()));
        if (!passwordEncoder.matches(passwordUpdateForProfile.oldPassword(), useFromDb.getPassword())) {
            throw new RuntimeException("Введен неверный текущий пароль");
        }
        if (!passwordUpdateForProfile.newPassword().equals(passwordUpdateForProfile.confirmPassword())) {
            throw new RuntimeException("confirm password don't equal new password");
        }
        useFromDb.setPassword(passwordEncoder.encode(passwordUpdateForProfile.newPassword()));
    }
}

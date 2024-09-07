package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.UserDto;
import ru.magicvolley.entity.ProfileEntity;
import ru.magicvolley.entity.RoleEntity;
import ru.magicvolley.entity.UserEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.ProfileRepository;
import ru.magicvolley.repository.UserRepository;
import ru.magicvolley.request.PasswordUpdateForProfile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Transactional
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto getById(UUID id) {
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, id));
    }

    @Transactional
    public UUID create(UserDto user) {

        Set<RoleEntity> roles = roleService.getRoles(user.getRoles());
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .login(user.getLogin())
                .roles(roles)
                .build();
        return create(userEntity);
    }

    public UUID create(UserEntity user) {

        userRepository.save(user);
        ProfileEntity profile = ProfileEntity.builder()
                .userId(user.getId())
                .fulName(user.getLogin())
                .build();
        profileRepository.save(profile);
        return user.getId();
    }

    @Transactional
    public UserEntity update(UserEntity user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public void updatePassword(PasswordUpdateForProfile passwordUpdateForProfile) {
        UserEntity useFromDb = userRepository.findById(passwordUpdateForProfile.id())
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, passwordUpdateForProfile.id()));
        if (!useFromDb.getPassword().equals(passwordEncoder.encode(passwordUpdateForProfile.oldPassword()))) {
            throw new RuntimeException("Введен неверный текущий пароль");
        }
        if (!passwordUpdateForProfile.newPassword().equals(passwordUpdateForProfile.confirmPassword())) {
            throw new RuntimeException("confirm password don't equal new password");
        }
        useFromDb.setPassword(passwordEncoder.encode(passwordUpdateForProfile.newPassword()));
    }

}

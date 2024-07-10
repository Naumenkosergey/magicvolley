package com.example.demomv.service;

import com.example.demomv.dto.UserDto;
import com.example.demomv.entity.UserEntity;
import com.example.demomv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity getById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity create(UserDto user) {
        return userRepository.save(UserEntity.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .login(user.getLogin())
                .build());
    }

    public UserEntity update(UserEntity user) {
        return userRepository.save(user);
    }

    public void delete(UUID id){
        userRepository.deleteById(id);
    }

}

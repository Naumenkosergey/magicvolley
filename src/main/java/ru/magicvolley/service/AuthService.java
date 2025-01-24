package ru.magicvolley.service;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.Util;
import ru.magicvolley.entity.RoleEntity;
import ru.magicvolley.entity.UserEntity;
import ru.magicvolley.enums.Role;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.exceptions.ValidationException;
import ru.magicvolley.repository.RoleRepository;
import ru.magicvolley.repository.UserRepository;
import ru.magicvolley.request.AddUserCampRequest;
import ru.magicvolley.request.LoginRequest;
import ru.magicvolley.request.SignUpRequest;
import ru.magicvolley.response.UserInfoResponse;
import ru.magicvolley.security.jwt.JwtUtils;
import ru.magicvolley.security.service.UserDetailsImpl;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserService userService;

    @Transactional
    public UserInfoResponse login(LoginRequest loginRequest) throws AuthException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (userDetails.isBlocked()) {
            throw new RuntimeException("Error:" + loginRequest.getUsername() + " is blocked!");
        }

        String cookie = jwtUtils.generateJwtCookie(userDetails).toString();

        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElseThrow(() -> new EntityNotFoundException(RoleEntity.class, Role.USER));

        return new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getTelephone(),
                userDetails.getEmail(),
                cookie,
                Set.of(role));
    }

    @Transactional
    public UserInfoResponse signup(SignUpRequest signUpRequest) {

        if (!signUpRequest.getTelephone().matches("[+?\\d]{11,12}")) {
            throw new ValidationException("Ошибка: Телефон введен неверно!");
        }

        if (userRepository.existsByTelephone(signUpRequest.getTelephone())) {
            throw new RuntimeException("Error: telephone is already in use!");
        }

        RoleEntity roleUserFromDb = roleRepository.findByRole(Role.USER)
                .orElseThrow(() -> new EntityNotFoundException(RoleEntity.class, Role.USER));

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .telephone(Util.trim(signUpRequest.getTelephone(), '+'))
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .isBlocked(Boolean.FALSE)
                .roleId(roleUserFromDb.getId())
                .role(roleUserFromDb)
                .build();
        userService.create(user);

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        String cookie = jwtUtils.generateJwtCookie(userDetails).toString();

        return new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getTelephone(),
                userDetails.getEmail(),
                cookie,
                Set.of(roleUserFromDb.getRole().name()));

    }

    @Transactional(propagation = Propagation.MANDATORY)
    public UserEntity addUserForCamp(AddUserCampRequest addUserCampRequest) {

        RoleEntity roleUserFromDb = roleRepository.findByRole(Role.USER)
                .orElseThrow(() -> new EntityNotFoundException(RoleEntity.class, Role.USER));

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .telephone(Util.trim(addUserCampRequest.getTelephone(), '+'))
                .username(StringUtils.isNoneBlank(addUserCampRequest.getUsername()) ? addUserCampRequest.getUsername() : addUserCampRequest.getTelephone())
                .password(encoder.encode(Util.trim(addUserCampRequest.getTelephone(), '+')))
                .isBlocked(Boolean.FALSE)
                .roleId(roleUserFromDb.getId())
                .build();
        userService.create(user);
        return user;
    }
}


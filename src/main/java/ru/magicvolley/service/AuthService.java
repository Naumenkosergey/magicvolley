package ru.magicvolley.service;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.entity.RoleEntity;
import ru.magicvolley.entity.UserEntity;
import ru.magicvolley.enums.Role;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.RoleRepository;
import ru.magicvolley.repository.UserRepository;
import ru.magicvolley.request.AddUserRequest;
import ru.magicvolley.request.LoginRequest;
import ru.magicvolley.request.SignUpRequest;
import ru.magicvolley.response.UserInfoResponse;
import ru.magicvolley.security.jwt.JwtUtils;
import ru.magicvolley.security.service.UserDetailsImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    private UserService userService;

    @Transactional
    public UserInfoResponse login(LoginRequest loginRequest) throws AuthException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String cookie = jwtUtils.generateJwtCookie(userDetails).toString();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                cookie,
                roles);
    }

    @Transactional
    public Boolean signup(SignUpRequest signUpRequest) {


        if (userRepository.existsByLogin(signUpRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByTelephone(signUpRequest.getTelephone())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        RoleEntity userRole = roleRepository.findByRole(Role.USER)
                .orElseThrow(() -> new EntityNotFoundException(RoleEntity.class, Role.USER));

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .telephone(signUpRequest.getTelephone())
                .login(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .roles(Set.of(userRole))
                .build();
        userService.create(user);

        return Boolean.TRUE;
    }

    public UUID getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetailsImpl) {
            return userDetailsImpl.getId();
        } else {
            throw new AuthenticationException("Наавторизованный пользователь") {
            };
        }
    }

    public Boolean isAdminCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetailsImpl) {
            return userDetailsImpl.getAuthorities().stream().anyMatch(r -> r.toString().equals(Role.ADMIN.name()));
        }
        return Boolean.FALSE;
    }

    public boolean addUser(AddUserRequest addUserRequest) {

        if (userRepository.existsByLogin(addUserRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByTelephone(addUserRequest.getTelephone())) {
            throw new RuntimeException("Error: Email is already in use!");
        }
        Set<RoleEntity> roles = new HashSet<>();
        if (addUserRequest.getIsUser()) {
            roles.add(roleRepository.findByRole(Role.USER)
                    .orElseThrow(() -> new EntityNotFoundException(RoleEntity.class, Role.USER)));
        }

        if (addUserRequest.getIsAdmin()) {
            roles.add(roleRepository.findByRole(Role.ADMIN)
                    .orElseThrow(() -> new EntityNotFoundException(RoleEntity.class, Role.ADMIN)));
        }

        if (addUserRequest.getIsModerator()) {
            roles.add(roleRepository.findByRole(Role.MODERATOR)
                    .orElseThrow(() -> new EntityNotFoundException(RoleEntity.class, Role.MODERATOR)));
        }

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .telephone(addUserRequest.getTelephone())
                .login(addUserRequest.getUsername())
                .password(encoder.encode(addUserRequest.getTelephone()))
                .roles(roles)
                .build();
        userService.create(user);

        return Boolean.TRUE;
    }

//    public JwtResponse getAccessToken(String refreshToken) throws AuthException {
//        if (jwtProvider.validateRefreshToken(refreshToken)) {
//
//            Claims refreshClaims = jwtProvider.getRefreshClaims(refreshToken);
//            String login = refreshClaims.getSubject();
//            String saveRefreshToken = refreshStorage.getHome(login);
//            if (Objects.nonNull(saveRefreshToken) && saveRefreshToken.equals(refreshToken)) {
//                UserEntity userEntity = userRepository.findByLogin(login)
//                        .orElseThrow(() -> new AuthException(String.format("Пользователь %s не найден", login)));
//                UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);
//                String accessToken = jwtProvider.generateAccessJwtToken(userDetails);
//                return new JwtResponse(accessToken, null, userEntity);
//            }
//        }
//        return new JwtResponse(null, null, null);
//    }

//    public JwtResponse refresh(String refreshToken) throws AuthException {
//        if (jwtProvider.validateRefreshToken(refreshToken)) {
//
//            Claims refreshClaims = jwtProvider.getRefreshClaims(refreshToken);
//            String login = refreshClaims.getSubject();
//            String saveRefreshToken = refreshStorage.getHome(login);
//            if (Objects.nonNull(saveRefreshToken) && saveRefreshToken.equals(refreshToken)) {
//                UserEntity userEntity = userRepository.findByLogin(login)
//                        .orElseThrow(() -> new AuthException(String.format("Пользователь %s не найден", login)));
//                UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);
//                String accessToken = jwtProvider.generateAccessJwtToken(userDetails);
//                String newRefreshToken = jwtProvider.generateRefreshJwtToken(userDetails);
//                refreshStorage.put(userEntity.getLogin(), newRefreshToken);
//                return new JwtResponse(accessToken, newRefreshToken, userEntity);
//            }
//        }
//        throw new AuthException("Невалидный JWT токен");
//
//    }

}

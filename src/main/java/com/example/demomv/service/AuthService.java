package com.example.demomv.service;

import com.example.demomv.entity.UserEntity;
import com.example.demomv.repository.UserRepository;
import com.example.demomv.request.LoginRequest;
import com.example.demomv.request.SignupRequest;
import com.example.demomv.respnse.JwtResponse;
import com.example.demomv.security.jwt.JwtProvider;
import com.example.demomv.security.service.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    private Map<String, String> refreshStorage = new HashMap<>();


    public JwtResponse login(LoginRequest loginRequest) throws AuthException {

        UserEntity userEntity = userRepository.findByLogin(loginRequest.getUsername())
                .orElseThrow(() -> new AuthException(String.format("пользователь %s не найден", loginRequest.getUsername())));
        if (userEntity.getPassword().equals(loginRequest.getPassword())) {
            UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);
            String accessToken = jwtProvider.generateAccessJwtToken(userDetails);
            String refreshToken = jwtProvider.generateRefreshJwtToken(userDetails);
            refreshStorage.put(userEntity.getLogin(), refreshToken);
            return new JwtResponse(accessToken, refreshToken, userEntity);

        }
        throw new AuthException("Неправильный пароль");

//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtProvider.generateAccessJwtToken(authentication);

//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        Set<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toSet());

//        return new JwtResponse(jwt, userDetails, roles);


    }

    public Boolean signup(SignupRequest signupRequest) {

        if (userRepository.existsByLogin(signupRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .email(signupRequest.getEmail())
                .login(signupRequest.getUsername())
                .password(encoder.encode(signupRequest.getPassword()))
                .build();

        user.setRoles(roleService.getRoles(signupRequest.getRoles()));
        userRepository.save(user);

        String s = jwtProvider.generateAccessJwtToken(new UserDetailsImpl(user));

        return Boolean.TRUE;
    }

    public JwtResponse getAccessToken(String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {

            Claims refreshClaims = jwtProvider.getRefreshClaims(refreshToken);
            String login = refreshClaims.getSubject();
            String saveRefreshToken = refreshStorage.get(login);
            if (Objects.nonNull(saveRefreshToken) && saveRefreshToken.equals(refreshToken)) {
                UserEntity userEntity = userRepository.findByLogin(login)
                        .orElseThrow(() -> new AuthException(String.format("Пользователь %s не найден", login)));
                UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);
                String accessToken = jwtProvider.generateAccessJwtToken(userDetails);
                return new JwtResponse(accessToken, null, userEntity);
            }
        }
        return new JwtResponse(null, null, null);
    }

    public JwtResponse refresh(String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {

            Claims refreshClaims = jwtProvider.getRefreshClaims(refreshToken);
            String login = refreshClaims.getSubject();
            String saveRefreshToken = refreshStorage.get(login);
            if (Objects.nonNull(saveRefreshToken) && saveRefreshToken.equals(refreshToken)) {
                UserEntity userEntity = userRepository.findByLogin(login)
                        .orElseThrow(() -> new AuthException(String.format("Пользователь %s не найден", login)));
                UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);
                String accessToken = jwtProvider.generateAccessJwtToken(userDetails);
                String newRefreshToken = jwtProvider.generateRefreshJwtToken(userDetails);
                refreshStorage.put(userEntity.getLogin(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken, userEntity);
            }
        }
        throw new AuthException("Невалидный JWT токен");

    }
}

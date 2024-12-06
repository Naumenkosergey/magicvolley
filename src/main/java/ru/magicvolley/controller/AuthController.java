package ru.magicvolley.controller;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.request.LoginRequest;
import ru.magicvolley.request.SignUpRequest;
import ru.magicvolley.response.MessageResponse;
import ru.magicvolley.response.UserInfoResponse;
import ru.magicvolley.security.jwt.JwtUtils;
import ru.magicvolley.service.AuthService;

@RestController
@RequestMapping("/magicvolley/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<UserInfoResponse> login(@RequestBody LoginRequest loginRequest) throws AuthException {
        UserInfoResponse login = authService.login(loginRequest);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, login.getCookie()).body(login);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signupRequest){
        boolean isCreateUser = authService.signup(signupRequest);
        return isCreateUser
                ? ResponseEntity.ok(new MessageResponse("User registered successfully!"))
                : ResponseEntity.badRequest().body(new MessageResponse("login or email already exists"));
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }



//    @PostMapping("/token")
//    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest refreshToken) throws AuthException {
//        JwtResponse token = authService.getAccessToken(refreshToken.getRefreshToken());
//        return new ResponseEntity<>(token, HttpStatus.OK);
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest refreshToken) throws AuthException {
//        JwtResponse token = authService.refresh(refreshToken.getRefreshToken());
//        return new ResponseEntity<>(token, HttpStatus.OK);
//    }
}

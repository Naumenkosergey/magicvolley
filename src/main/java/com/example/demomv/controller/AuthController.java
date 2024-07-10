package com.example.demomv.controller;

import com.example.demomv.request.LoginRequest;
import com.example.demomv.request.RefreshJwtRequest;
import com.example.demomv.request.SignupRequest;
import com.example.demomv.respnse.JwtResponse;
import com.example.demomv.service.AuthService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) throws AuthException {
        JwtResponse login = authService.login(loginRequest);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody SignupRequest signupRequest){
        Boolean signup = authService.signup(signupRequest);
        return new ResponseEntity<>(signup, HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest refreshToken) throws AuthException {
        JwtResponse token = authService.getAccessToken(refreshToken.getRefreshToken());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest refreshToken) throws AuthException {
        JwtResponse token = authService.refresh(refreshToken.getRefreshToken());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }



}

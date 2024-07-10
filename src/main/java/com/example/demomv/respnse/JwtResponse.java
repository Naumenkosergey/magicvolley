package com.example.demomv.respnse;

import com.example.demomv.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JwtResponse {

    private String accessToken;
    private String refreshToken;
    private String type = "Bearer";
    private UUID id;
    private String username;
    private String email;
    private Set<String> roles;

    public JwtResponse(String accessToken, String refreshToken, UserEntity userEntity) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = userEntity.getId();
        this.username = userEntity.getLogin();
        this.email = userEntity.getEmail();
        this.roles = userEntity.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toSet());
    }

//    public JwtResponse(String accessToken, String refreshToken, UserDetailsImpl userDetails, Set<String> roles) {
//        this.accessToken = accessToken;
//        this.refreshToken = refreshToken;
//        this.id = userDetails.getId();
//        this.username = userDetails.getUsername();
//        this.email = userDetails.getEmail();
//        this.roles = roles;
//    }
}

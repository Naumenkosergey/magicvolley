package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.UserEntity;

import java.util.UUID;

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
    private String role;

    public JwtResponse(String accessToken, String refreshToken, UserEntity userEntity) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole().getRole().name();
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

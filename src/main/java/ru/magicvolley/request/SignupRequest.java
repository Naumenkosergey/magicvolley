package ru.magicvolley.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SignupRequest {

    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}

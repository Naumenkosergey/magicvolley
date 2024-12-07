package ru.magicvolley.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.UserEntity;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {

    private UUID id;
    private String email;
    private String login;
    private Set<String> roles;
    private UUID avatar;

    public UserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.login = userEntity.getUsername();
        this.roles = userEntity.getRoles()
                .stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet());
    }

}

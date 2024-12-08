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
    private String username;
    private String telephone;
    private Set<String> roles;

    public UserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.telephone = userEntity.getTelephone();
        this.roles = userEntity.getRoles()
                .stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet());
    }

}

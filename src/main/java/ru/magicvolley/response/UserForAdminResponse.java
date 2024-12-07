package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.UserEntity;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserForAdminResponse {

    private UUID id;
    private String name;
    private String telephone;
    private Boolean isAdmin;
    private Boolean isUser;
    private Boolean isModerator;
    private MediaStorageInfo avatar;

    public UserForAdminResponse(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.name = userEntity.getUsername();
        this.telephone = userEntity.getTelephone();
        this.isAdmin = userEntity.getRoles().stream().anyMatch(role -> role.getRole().name().equals("ADMIN"));
        this.isModerator = userEntity.getRoles().stream().anyMatch(role -> role.getRole().name().equals("MODERATOR"));
        this.isUser = userEntity.getRoles().stream().anyMatch(role -> role.getRole().name().equals("USER"));
    }

}

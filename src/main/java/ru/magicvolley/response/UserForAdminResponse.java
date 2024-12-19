package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.dto.UserDto;
import ru.magicvolley.entity.UserEntity;
import ru.magicvolley.enums.Role;

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
        this.isAdmin = userEntity.getRole().getRole().name().equals(Role.ADMIN.name());
        this.isModerator = userEntity.getRole().getRole().name().equals(Role.MODERATOR.name());
        this.isUser = userEntity.getRole().getRole().name().equals(Role.USER.name());
    }

    public UserForAdminResponse(UserDto userDto, MediaStorageInfo avatar) {
        this.id = userDto.getId();
        this.name = userDto.getUsername();
        this.telephone = userDto.getTelephone();
        this.isAdmin = userDto.getRole().equals(Role.ADMIN.name());
        this.isModerator = userDto.getRole().equals(Role.MODERATOR.name());
        this.isUser = userDto.getRole().equals(Role.USER.name());
        this.avatar = avatar;
    }

}

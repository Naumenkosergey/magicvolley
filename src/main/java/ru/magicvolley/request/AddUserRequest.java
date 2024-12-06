package ru.magicvolley.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddUserRequest {

    private String username;
    private String telephone;
    private Boolean isAdmin;
    private Boolean isUser;
    private Boolean isModerator;
}

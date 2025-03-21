package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NotificationResponse {

    UUID campId;
    String campName;
    Integer countNewUsers;

}

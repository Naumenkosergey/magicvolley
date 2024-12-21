package ru.magicvolley.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddUserCampRequest {

    private String telephone;
    private String username;
    private Integer bookingCount;
    private UUID campId;
}

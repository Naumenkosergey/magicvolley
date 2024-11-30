package ru.magicvolley.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.CampUserEntity;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CampUserDto {

    private UUID id;
    private String login;
    private Boolean bookingConfirmed;
    private Boolean isReserved;


    public CampUserDto(CampUserEntity campUserEntity) {
        this.id = campUserEntity.getUser().getId();
        this.login = campUserEntity.getUser().getLogin();
        this.bookingConfirmed = campUserEntity.getBookingConfirmed();
        this.isReserved = campUserEntity.getIsReserved();
    }

}

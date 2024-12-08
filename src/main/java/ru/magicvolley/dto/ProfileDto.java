package ru.magicvolley.dto;

import ru.magicvolley.response.UserForAdminResponse;

import java.time.LocalDate;
import java.util.List;

public record ProfileDto(
        String fullName,
        LocalDate birthday,
        String email,
        String telephone,
        List<CampDtoForList> pastCamps,
        List<CampDtoForList> nearestCamps,
        MediaStorageInfo avatar,
        List<UserForAdminResponse> users,
        boolean isAdmin
) {
}




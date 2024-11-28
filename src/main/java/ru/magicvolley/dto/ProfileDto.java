package ru.magicvolley.dto;

import java.time.LocalDate;
import java.util.List;

public record ProfileDto(
        String fullName,
        LocalDate birthday,
        String email,
        String telephone,
        List<UserProfileCampDto> pastCamps,
        List<UserProfileCampDto> nearestCamps,
        MediaStorageInfo avatar
) {
}




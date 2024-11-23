package ru.magicvolley.dto;

import java.time.LocalDate;
import java.util.List;

public record ProfileDto(
        String fulName,
        LocalDate birthday,
        String email,
        String telephone,
        List<CampDto> pastCamps,
        List<CampDto> nearestCamps
) {
}




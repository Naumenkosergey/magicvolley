package ru.magicvolley.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProfileDto(
        String fulName,
        LocalDate birthday,
        String email,
        String telephone,
        List<Camp> pastCamps,
        List<Camp> nearestCamps
) {

    public record Camp(
            UUID id,
            String name,
            String info,
            BigDecimal price,
            LocalDate dateStart,
            LocalDate dateEnd,
            List<CoachDto> coaches) {
    }
}




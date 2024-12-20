package ru.magicvolley.dto;

import java.util.UUID;

public record ReservationDto(
        UUID userId,
        UUID campId,
        String username,
        String telephone,
        int bookingCount) {
}

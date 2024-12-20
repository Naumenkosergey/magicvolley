package ru.magicvolley.dto;

import java.util.UUID;

public record ReservationDto(
        UUID userId,
        UUID campId,
        String name,
        String telephone,
        int bookingCount) {
}

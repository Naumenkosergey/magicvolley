package ru.magicvolley.dto;

import java.util.UUID;

public record ConfirmReservationDto(
        UUID userId,
        UUID campId,
        boolean isConfirm,
        int count) {
}

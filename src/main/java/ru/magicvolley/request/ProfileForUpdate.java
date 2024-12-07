package ru.magicvolley.request;

import java.time.LocalDate;
import java.util.UUID;

public record ProfileForUpdate(
        UUID id,
        String fullName,
        LocalDate birthday,
        String telephone
) {
}

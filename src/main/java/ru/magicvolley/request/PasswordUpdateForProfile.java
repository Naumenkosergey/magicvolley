package ru.magicvolley.request;

import java.util.UUID;

public record PasswordUpdateForProfile(
        UUID id,
        String oldPassword,
        String newPassword,
        String confirmPassword
) {
}

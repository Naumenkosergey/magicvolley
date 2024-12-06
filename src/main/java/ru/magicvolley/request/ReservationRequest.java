package ru.magicvolley.request;


import java.util.UUID;

public record ReservationRequest(UUID campId, UUID userId) {
}

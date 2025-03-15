package ru.magicvolley.request;

import java.util.UUID;

public record ProgramRequest(
        UUID id,
        String info,
        String dayOfWeek,
        Integer order,
        UUID campId
){}

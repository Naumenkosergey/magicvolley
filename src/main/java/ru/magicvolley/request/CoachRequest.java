package ru.magicvolley.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.dto.MediaStorageInfo;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachRequest {

    private UUID id;
    private String name;
    private String surename;
    private String infos;
    private MediaStorageInfo mainImage;
}

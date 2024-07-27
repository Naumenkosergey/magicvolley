package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.CoachEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachResponse {

    private UUID id;
    private String name;
    private String surename;
    private List<String> infos;
    private MediaStorageInfo mainImage;

    public CoachResponse(CoachEntity coach) {
        this.id = coach.getId();
        this.name = coach.getCoachName();
        this.surename = coach.getSurename();
        this.infos = Arrays.stream(coach.getInfo().split(";")).toList();
        this.mainImage = new MediaStorageInfo(coach.getAvatar());
    }
}

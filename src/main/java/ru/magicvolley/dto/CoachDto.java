package ru.magicvolley.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.CoachEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachDto {

    private UUID id;
    private String name;
    private List<String> infos;
    private String promo;
    private MediaStorageInfo mainImage;

    public CoachDto(CoachEntity coach) {
        this.id = coach.getId();
        this.name = coach.getCoachName();
        this.infos = Arrays.stream(coach.getInfo().split(";")).toList();
        this.promo = coach.getPromo();
        this.mainImage = Objects.nonNull(coach.getAvatar()) ? new MediaStorageInfo(coach.getAvatar()) : null;
    }
}

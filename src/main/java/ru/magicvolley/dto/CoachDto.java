package ru.magicvolley.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.Util;
import ru.magicvolley.entity.CoachEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ru.magicvolley.enums.CoachType.BEACH;
import static ru.magicvolley.enums.CoachType.CLASSIC;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachDto {

    private UUID id;
    private String name;
    private List<String> infos;
    private String promo;
    private Boolean isBeach;
    private Boolean isClassic;
    private MediaStorageInfo mainImage;
    private boolean isVisible;

    public CoachDto(CoachEntity coach, String prefixUrlMedia) {
        this.id = coach.getId();
        this.name = coach.getCoachName();
        this.infos = Arrays.stream(coach.getInfo().split(";")).toList();
        this.promo = coach.getPromo();
        this.isBeach = coach.getCoachType().contains(BEACH.name());
        this.isClassic = coach.getCoachType().contains(CLASSIC.name());
        this.mainImage = Objects.nonNull(coach.getAvatar()) ? new MediaStorageInfo(coach.getAvatar(), prefixUrlMedia) : null;
        this.isVisible = Util.getOrDefaultIfNull(coach.isVisible(), Boolean.TRUE);
    }
}
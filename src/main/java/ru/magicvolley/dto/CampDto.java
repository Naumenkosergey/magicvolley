package ru.magicvolley.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampDto {

    private UUID id;
    private String name;
    private String info;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String dateString;
    private Integer countAll;
    private Integer countFree;
    private MediaStorageInfo mainImage;
    private MediaStorageInfo imageCart;
    private List<MediaStorageInfo> images;
    private List<CoachDto> coaches;
    private List<CampPackageCardDto> packages;
    private List<CampUserDto> users;
    private List<MediaStorageInfo> gallery;
    private ProgramForCamp program;
}

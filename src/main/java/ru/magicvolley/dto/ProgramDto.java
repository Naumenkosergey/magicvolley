package ru.magicvolley.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramDto {
    private UUID id;
    private String info;
    private String dayOfWeek;
    private Integer order;
    private UUID campId;

}
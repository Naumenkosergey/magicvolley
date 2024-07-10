package com.example.demomv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampDto {

    private UUID id;
    private String name;
    private String info;
    private BigDecimal price;
    private String dateStart;
    private String dateEnd;
    private Integer countAll;
    private Integer countFree;
    private List<UUID> coaches;
}

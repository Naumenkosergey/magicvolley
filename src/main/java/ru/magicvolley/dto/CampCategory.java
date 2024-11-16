package ru.magicvolley.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.enums.CampType;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CampCategory {

    private CampType campType;


}

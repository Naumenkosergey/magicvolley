package com.example.demomv.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "camps")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class CampEntity {

    @Id
    private UUID id;
    private String name;
    private String info;
    private BigDecimal price;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer countAll;
    private Integer countFree;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "campId", insertable = false, updatable = false)
    private List<CampCoachEntity> campCoaches;
}

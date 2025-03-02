package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.magicvolley.enums.CampType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "camps")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CampEntity {

    @Id
    private UUID id;
    private String campName;
    private String info;
    private String place;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer countAll;
    @Enumerated(EnumType.STRING)
    private CampType campType;
    @Column(name = "main_image_id")
    private UUID mainImageId;
    @Column(name = "cart_image_id")
    private UUID cartImageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_image_id", insertable = false, updatable = false)
    private MediaStorageEntity mainImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_image_id", insertable = false, updatable = false)
    private MediaStorageEntity imageCart;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "camp_coaches",
            joinColumns = @JoinColumn(name = "camp_id"),
            inverseJoinColumns = @JoinColumn(name = "coach_id")
    )
    private List<CoachEntity> coaches;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_id", insertable = false, updatable = false)
    private List<CampPackageCardEntity> packages;



    @Version
    private Long version;
}

package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "camp_package_card")
@EqualsAndHashCode(of = "id")
public class CampPackageCardEntity {

    @EmbeddedId
    private Id id;

    private  String info;

    private Integer totalPrice;
    private Integer bookingPrice;

    private Integer firstPrice;
    private String firstLimitation;

    private Integer secondPrice;
    private String secondLimitation;

    private Integer thirdPrice;
    private String thirdLimitation;

    @ManyToOne
    @JoinColumn(name = "camp_id", insertable = false, updatable = false)
    private CampEntity camp;

    @ManyToOne
    @JoinColumn(name = "package_card_id", insertable = false, updatable = false)
    private PackageCardEntity packageCard;

    @Version
    private Long version;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id {
        @Column(name = "camp_id")
        private UUID campId;
        @Column(name = "package_card_id")
        private Integer packageCardId;
    }
}
package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "package_card")
@EqualsAndHashCode(of = "id")
public class PackageCardEntity {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;
    private String namePackage;
    private String info;
    private String costNamingLink;
    @Version
    private Long version;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_card_id", insertable = false, updatable = false)
    private List<CampPackageCardEntity> campPackageCards;


}

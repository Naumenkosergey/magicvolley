package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "package_card")
public class PackageCardEntity {

    @Id
    private Integer id;
    private String name;
    private String info;
    private String costNamingLink;
    private Long version;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_card_id", insertable = false, updatable = false)
    private List<CampPackageCardEntity> campPackageCards;


}

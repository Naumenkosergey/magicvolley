package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "home_page")
public class HomePageEntity {

    @Id
    private UUID id;
    private String title;
    private String subtitle;

    @Column(name = "main_image_id")
    private UUID mainImageId;

    private String linkVk;
    private String linkTg;
    private String linkInstagram;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    List<ManagerEntity> managers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_image_id", insertable = false, updatable = false)
    private MediaStorageEntity mainImage;
}

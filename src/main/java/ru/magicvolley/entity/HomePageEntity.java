package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "home_page")
@EqualsAndHashCode(of = "id")
public class HomePageEntity {

    @Id
    private UUID id;
    private String title;
    private String subtitle;

    @Column(name = "main_image_id")
    private UUID mainImageId;



    @Column(name = "image_admin_id")
    private UUID imageAdminId;
    private String textUnderImage;
    private String linkVk;
    private String linkTg;
    private String linkInstagram;
    private String email;
    private String contacts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_image_id", insertable = false, updatable = false)
    private MediaStorageEntity mainImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_admin_id", insertable = false, updatable = false)
    private MediaStorageEntity imageAdmin;

}

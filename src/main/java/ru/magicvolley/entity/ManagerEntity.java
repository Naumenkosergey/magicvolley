package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "managers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ManagerEntity {

    @Id
    @Column(name = "id")
    private UUID id;
    private String textUnderImage;
    private String email;
    private String contacts;

    @Column(name = "image_admin_id")
    private UUID imageAdminId;

    @Version
    private Long version;

    @Column(name = "home_page_id")
    private UUID homePageId;

    @ManyToOne
    @JoinColumn(name = "home_page_id", insertable = false, updatable = false)
    private HomePageEntity homePage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_admin_id", insertable = false, updatable = false)
    private MediaStorageEntity imageAdmin;

}

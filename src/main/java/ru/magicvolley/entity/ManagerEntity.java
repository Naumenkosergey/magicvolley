package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "managers")
public class ManagerEntity {

    @Id
    private UUID id;
    private String textUnderImage;
    private String email;
    private String contacts;

    @Version
    private Long version;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_admin_id", insertable = false, updatable = false)
    private MediaStorageEntity imageAdmin;

}

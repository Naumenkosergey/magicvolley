package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "master")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MasterEntity {

    @Id
    private UUID id;
    private String name_master;
    @Column(name = "image_id")
    private UUID avatarMasterId;
    private String info;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private MediaStorageEntity avatarMaster;

    @Version
    private Long version;
}

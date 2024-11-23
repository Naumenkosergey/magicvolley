package ru.magicvolley.entity;


import jakarta.persistence.*;
import lombok.*;
import ru.magicvolley.enums.TypeEntity;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "media_storages")
@EqualsAndHashCode(of = "id")
public class MediaStorageEntity {

    @Id
    private UUID id;
    @Column(name = "entity_id")
    private UUID entityId;
    @Column(name = "file_name")
    private String fileName;
    private Long size;
    @Enumerated(EnumType.STRING)
    private TypeEntity typeEntity;
    @Basic
    private byte[] data;
    private String contentType;
    @Version
    private Long version;
}

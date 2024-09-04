package ru.magicvolley.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.magicvolley.enums.TypeEntity;

import java.time.LocalDateTime;
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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_id", insertable = false, updatable = false)
    private CampEntity camp;


}

package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "coaches")
@EqualsAndHashCode(of = "id")
public class CoachEntity {

    @Id
    private UUID id;
    private String coachName;
    private String info;
    private String promo;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Version
    private Long version;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private MediaStorageEntity avatar;

    @ManyToMany(mappedBy = "coaches")
    private List<CampEntity> camps;
}

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
public class CoachEntity {

    @Id
    private UUID id;
    private String coachName;
    private String info;
    private String promo;
    @Column(name = "coach_type")
    private String coachType;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Version
    private Long version;
    private boolean isVisible;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private MediaStorageEntity avatar;

    @ManyToMany(mappedBy = "coaches")
    private List<CampEntity> camps;
}

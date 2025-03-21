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
@Table(name = "camp_users")
public class CampUserEntity {
    @EmbeddedId
    private Id id;

    private Boolean bookingConfirmed;
    private Boolean isReserved;
    private Boolean isPast;
    private Integer bookingCount;
    private Boolean isViewed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_id", insertable = false, updatable = false)
    private CampEntity camp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Id {
        @Column(name = "user_id")
        private UUID userId;
        @Column(name = "camp_id")
        private UUID campId;
    }
}

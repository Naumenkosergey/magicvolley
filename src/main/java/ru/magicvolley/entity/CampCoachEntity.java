package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "camp_coaches")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CampCoachEntity {

    @EmbeddedId
    private Id id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_id", insertable = false, updatable = false)
    private CampEntity camp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id", insertable = false, updatable = false)
    private CoachEntity coach;

    @Version
    private Long version;



    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "camp_id")
        private UUID campId;
        @Column(name = "coach_id")
        private UUID coachId;
    }
}

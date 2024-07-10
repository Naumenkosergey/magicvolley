package com.example.demomv.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "camp_coach")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class CampCoachEntity {

    @EmbeddedId
    private Id id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campId", insertable = false, updatable = false)
    private CampEntity camp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coachId", insertable = false, updatable = false)
    private CoachEntity coach;



    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class Id implements Serializable {
        private UUID campId;
        private UUID coachId;
    }
}

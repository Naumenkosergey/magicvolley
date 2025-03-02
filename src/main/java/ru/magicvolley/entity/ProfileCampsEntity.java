package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "profile_camps")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCampsEntity {

    @EmbeddedId
    private Id id;

    private Boolean isPast;
    private Boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "campId", insertable = false, updatable = false)
    private CampEntity camp;

    @Version
    private Long version;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id {
        private UUID profileId;
        private UUID campId;
    }
}

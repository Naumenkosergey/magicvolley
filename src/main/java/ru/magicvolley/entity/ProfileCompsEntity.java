package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "profile_camps")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCompsEntity {

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

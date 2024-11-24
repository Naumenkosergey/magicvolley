package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "camp_users")
@EqualsAndHashCode(of = "id")
public class CampUserEntity {
    @EmbeddedId
    private Id id;

    private Boolean bookingConfirmed;
    private Boolean isReserved;
    private Boolean isPast;

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

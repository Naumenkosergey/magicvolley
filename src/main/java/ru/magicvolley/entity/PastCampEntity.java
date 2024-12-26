package ru.magicvolley.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "past_camp_media")
@EqualsAndHashCode(of = "id")
public class PastCampEntity {

    @EmbeddedId
    private Id id;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "camp_id")
        private UUID campId;
        @Column(name = "media_id")
        private UUID mediaId;
    }
}

package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private MediaStorageEntity avatar;

    @Version
    private Long version;


    @ManyToMany(mappedBy = "coaches")
    private List<CampEntity> camps;
}

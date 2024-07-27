package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_profile")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntity {

    @Id
    private UUID userId;
    private String fulName;
    private String telephone;
    private LocalDate birthday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imageId", insertable = false, updatable = false)
    private MediaStorageEntity avatar;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profileId", insertable = false, updatable = false)
    private Set<ProfileCompsEntity> profileComps;

    @Version
    private Long version;
}

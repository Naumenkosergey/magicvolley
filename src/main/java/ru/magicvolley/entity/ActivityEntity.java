package ru.magicvolley.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "activity")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ActivityEntity {

    @Id
    private UUID id;
    private String title;

    @Version
    private Long version;
}

package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "about_us_page")
@EqualsAndHashCode(of = "id")
public class AboutUsPageEntity {

    @Id
    private UUID id;
    private String title;
    private String subtitleFirst;
    private String subtitleSecond;
    @Version
    private Long version;
}

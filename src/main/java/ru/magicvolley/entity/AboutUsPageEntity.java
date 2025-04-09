package ru.magicvolley.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "about_us_page")
public class AboutUsPageEntity {

    @Id
    private UUID id;
    private String title;
    private String subtitleFirst;
    private String subtitleSecond;
    private String subtitleThird;
    private Integer numberOfWorkouts;
    private Integer numberOfCamps;
    private Integer numberOfStudents;
    @Version
    private Long version;
}

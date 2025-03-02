package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReviewEntity {

    @Id
    private UUID id;
    private String reviewText;
    private LocalDate dateReview;
    private String nameReviewer;
    @Column(name = "image_id")
    private UUID avatarReviewerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private MediaStorageEntity avatarReviewer;

    @Version
    private Long version;
}

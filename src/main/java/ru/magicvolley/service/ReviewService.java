package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.entity.ReviewEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.ReviewRepository;
import ru.magicvolley.request.AboutUsRequest;
import ru.magicvolley.response.AboutUsResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MediaService mediaService;
    @Value("${media.prefix.url}")
    private String prefixUrlMedia;


    public List<AboutUsResponse.Review> getReviews() {
        List<ReviewEntity> reviews = reviewRepository.findAll().stream()
                .sorted((o1, o2) -> o2.getDateReview().compareTo(o1.getDateReview()))
                .toList();

        return reviews.stream().map(reviewEntity ->
                AboutUsResponse.Review.builder()
                        .name(reviewEntity.getNameReviewer())
                        .image(new MediaStorageInfo(reviewEntity.getAvatarReviewer(), prefixUrlMedia))
                        .date(reviewEntity.getDateReview().toString())
                        .comment(reviewEntity.getReviewText())
                        .build()
        ).toList();
    }

    public void setReviews(List<AboutUsRequest.Review> reviewsRequest) {

        List<ReviewEntity> reviewEntities = reviewsRequest.stream()
                .map(reviewRequest -> {

                    UUID id = UUID.randomUUID();
                    return ReviewEntity.builder()
                            .id(id)
                            .dateReview(LocalDate.parse(reviewRequest.getDate()))
                            .reviewText(reviewRequest.getComment())
                            .avatarReviewer(mediaService.mediaInfoToMediaStorage(reviewRequest.getImage(), id))
                            .build();
                })
                .toList();

        reviewRepository.saveAll(reviewEntities);

    }

    @Transactional
    public Boolean updateReview(AboutUsRequest.Review reviewRequest) {
        LocalDate dateReviewRequest = LocalDate.parse(reviewRequest.getDate());
        ReviewEntity reviewFromDb = reviewRepository.findByNameReviewerAndDateReview(reviewRequest.getName(), dateReviewRequest)
                .orElseThrow(() -> new EntityNotFoundException(ReviewEntity.class, reviewRequest.getName()));

        MediaStorageEntity avatarReviewer = mediaService.mediaInfoToMediaStorage(reviewRequest.getImage(), reviewFromDb.getId());
        reviewFromDb.setDateReview(dateReviewRequest);
        reviewFromDb.setReviewText(reviewRequest.getComment());
        reviewFromDb.setNameReviewer(reviewRequest.getName());
        reviewFromDb.setAvatarReviewer(avatarReviewer);
        reviewFromDb.setAvatarReviewerId(avatarReviewer.getId());
        return null;
    }
}

package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.entity.ReviewEntity;
import ru.magicvolley.enums.TypeEntity;
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

    @Transactional
    public Boolean updateReview(AboutUsRequest.Review reviewRequest, UUID reviewId) {
        LocalDate dateReviewRequest = LocalDate.parse(reviewRequest.getDate());
        ReviewEntity reviewFromDb = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(ReviewEntity.class, reviewId));

        MediaStorageEntity avatarReviewer = mediaService.mediaInfoToMediaStorage(reviewRequest.getImage(), reviewFromDb.getId());
        reviewFromDb.setDateReview(dateReviewRequest);
        reviewFromDb.setReviewText(reviewRequest.getComment());
        reviewFromDb.setNameReviewer(reviewRequest.getName());
        reviewFromDb.setAvatarReviewer(avatarReviewer);
        reviewFromDb.setAvatarReviewerId(avatarReviewer.getId());
        return null;
    }

    @Transactional
    public UUID createReview(AboutUsRequest.Review reviewRequest) {

        UUID id = UUID.randomUUID();
        MediaStorageEntity avatarReviewer = mediaService.mediaInfoToMediaStorage(reviewRequest.getImage(), id);
        ReviewEntity reviewNew = ReviewEntity.builder()
                .id(id)
                .nameReviewer(reviewRequest.getName())
                .dateReview(LocalDate.parse(reviewRequest.getDate()))
                .reviewText(reviewRequest.getComment())
                .avatarReviewer(avatarReviewer)
                .avatarReviewerId(avatarReviewer.getId())
                .build();

        reviewRepository.save(reviewNew);
        return reviewNew.getId();
    }

    @Transactional
    public Boolean delete(UUID reviewId) {
        ReviewEntity reviewFomDb = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(ReviewEntity.class, reviewId));
        mediaService.delete(reviewFomDb.getId(), TypeEntity.REVIEW);
        reviewRepository.delete(reviewFomDb);
        return true;
    }
}


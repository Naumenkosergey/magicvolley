package ru.magicvolley.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.request.AboutUsRequest;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.ReviewService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/magicvolley/review", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PutMapping("/{reviewId}")
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<Boolean> updateReview(@RequestBody AboutUsRequest.Review reviewRequest, @PathVariable UUID reviewId) {
        return new ApiResponse<>(reviewService.updateReview(reviewRequest, reviewId));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> addActivity(@RequestBody AboutUsRequest.Review activityRequest) {
        return new ApiResponse<>(reviewService.createReview(activityRequest));
    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean> delete(@PathVariable UUID reviewId) {
        return new ApiResponse<>(reviewService.delete(reviewId));
    }
}

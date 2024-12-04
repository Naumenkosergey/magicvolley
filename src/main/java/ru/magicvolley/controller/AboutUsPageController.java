package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.request.AboutUsRequest;
import ru.magicvolley.response.AboutUsResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.AboutUsPageService;
import ru.magicvolley.service.ActivityService;
import ru.magicvolley.service.ReviewService;

@RestController
@RequestMapping(value = "/magicvolley/about", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AboutUsPageController {

    private final AboutUsPageService aboutUsPageService;
    private final ActivityService activityService;
    private final ReviewService reviewService;

    @GetMapping()
    public ApiResponse<AboutUsResponse> getAbout() {
        return new ApiResponse<>(aboutUsPageService.getAbout());
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<Boolean> update(@RequestBody AboutUsRequest aboutUsRequest) {
        return new ApiResponse<>(aboutUsPageService.update(aboutUsRequest));
    }

    @PutMapping("/activity/update/{id}")
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<Boolean> updateActivity(@RequestBody AboutUsRequest.Activity activityRequest) {
        return new ApiResponse<>(activityService.updateActivate(activityRequest));
    }

    @PutMapping("/review/update/{id}")
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<Boolean> updateReview(@RequestBody AboutUsRequest.Review reviewRequest) {
        return new ApiResponse<>(reviewService.updateReview(reviewRequest));
    }
}

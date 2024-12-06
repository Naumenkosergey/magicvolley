package ru.magicvolley.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.request.AboutUsRequest;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.ActivityService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/magicvolley/activity", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> addActivity(@RequestBody AboutUsRequest.Activity activityRequest) {
        return new ApiResponse<>(activityService.createActivity(activityRequest));
    }

    @PutMapping("/{activityId}")
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<Boolean> updateActivity(@RequestBody AboutUsRequest.Activity activityRequest, @PathVariable UUID activityId) {
        return new ApiResponse<>(activityService.updateActivate(activityRequest, activityId));
    }

    @DeleteMapping("/{activityId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean> delete(@PathVariable UUID activityId) {
        return new ApiResponse<>(activityService.delete(activityId));
    }
}

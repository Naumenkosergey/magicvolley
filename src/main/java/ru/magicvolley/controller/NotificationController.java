package ru.magicvolley.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magicvolley.response.NotificationResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.CampUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/magicvolley/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {

    CampUserService campUserService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<NotificationResponse>> getNotifications() {
        return new ApiResponse<>(campUserService.getNotifications());

    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Integer> getCountNotifications() {
        return new ApiResponse<>(campUserService.getCountNewNotifications());

    }
}

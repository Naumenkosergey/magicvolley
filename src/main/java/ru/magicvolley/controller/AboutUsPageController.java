package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magicvolley.response.AboutResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.AboutUsPageService;

@RestController
@RequestMapping(value = "/magicvolley/about", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AboutUsPageController {

    private final AboutUsPageService aboutUsPageService;
    @GetMapping()
    public ApiResponse<AboutResponse> getAbout() {
        return new ApiResponse<>(aboutUsPageService.getAbout());
    }
}

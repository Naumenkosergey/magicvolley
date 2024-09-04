package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magicvolley.response.HomePageResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.HomeService;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping()
//    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<HomePageResponse>  getHome() {
        return new ApiResponse<>(homeService.getHome());
    }
}

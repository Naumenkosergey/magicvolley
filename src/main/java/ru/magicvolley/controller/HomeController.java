package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.request.HomeContactBlockRequest;
import ru.magicvolley.request.HomeMainBlockRequest;
import ru.magicvolley.response.HomePageResponse;
import ru.magicvolley.response.LinkInfoResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.HomeService;

import java.util.UUID;

@RestController
@RequestMapping(value = {"/magicvolley/home", "/magicvolley"})
@AllArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping()
    public ApiResponse<HomePageResponse>  getHome() {
        return new ApiResponse<>(homeService.getHome());
    }

    @PutMapping("/main")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID>  updateMainBlock(@RequestBody HomeMainBlockRequest request) {
        return new ApiResponse<>(homeService.updateMainBlock(request));
    }

    @PutMapping("/contact")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID>  updateContactBlock(@RequestBody HomeContactBlockRequest request) {
        return new ApiResponse<>(homeService.updateContactBlock(request));
    }

    @GetMapping("/app_links")
    public ApiResponse<LinkInfoResponse>  getAppLinks() {
        return new ApiResponse<>(homeService.getAppLinks());
    }

}

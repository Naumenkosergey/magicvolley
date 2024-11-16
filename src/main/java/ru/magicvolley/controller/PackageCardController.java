package ru.magicvolley.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magicvolley.dto.PackageCardDto;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.PackageCardService;

import java.util.List;

@RestController
@RequestMapping("/magicvolley/package-card")
@RequiredArgsConstructor
public class PackageCardController {

    private final PackageCardService packageCardService;

    @GetMapping("/dropdown")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<PackageCardDto>> getAll(){
        return new ApiResponse<>(packageCardService.getDropdown());
    }

}

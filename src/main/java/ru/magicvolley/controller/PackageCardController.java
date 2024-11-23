package ru.magicvolley.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<PackageCardDto>> getAll() {
        return new ApiResponse<>(packageCardService.getDropdown());
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Integer> create(@RequestBody PackageCardDto packageCardRequest) {
        return new ApiResponse<>(packageCardService.create(packageCardRequest));

    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Integer> update(@RequestBody PackageCardDto packageCardRequest) {
        return new ApiResponse<>(packageCardService.update(packageCardRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean> update(@PathVariable Integer id) {
        return new ApiResponse<>(packageCardService.delete(id));
    }

}

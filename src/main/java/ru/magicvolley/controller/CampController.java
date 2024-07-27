package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.CampDto;
import ru.magicvolley.entity.CampEntity;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.CampService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/camps")
@AllArgsConstructor
public class CampController {

    private final CampService campService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<List<CampDto>> getAll() {
        return new ApiResponse<>(campService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<CampDto> getAll(@PathVariable UUID id) {
        return new ApiResponse<>(campService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<CampEntity> create(@RequestBody CampDto camp) {
        return new ApiResponse<>(campService.create(camp));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<CampEntity> update(@RequestBody CampEntity camp) {
        return new ApiResponse<>(campService.update(camp));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpStatus delete(@PathVariable UUID id) {
        campService.delete(id);
        return HttpStatus.OK;
    }
}

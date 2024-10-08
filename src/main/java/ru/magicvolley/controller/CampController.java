package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.CampDto;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.CampService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/magicvolley/camps", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CampController {

    private final CampService campService;

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<List<CampDto>> getAll() {
        return new ApiResponse<>(campService.getAll());
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<CampDto> getAll(@PathVariable UUID id) {
        return new ApiResponse<>(campService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> create(@RequestBody CampDto camp) {
        return new ApiResponse<>(campService.create(camp));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<UUID> update(@RequestBody CampDto camp) {
        return new ApiResponse<>(campService.update(camp));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean> delete(@PathVariable UUID id) {
        return new ApiResponse<>(campService.delete(id));
    }
}

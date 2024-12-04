package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.CampDto;
import ru.magicvolley.dto.CampDtoForList;
import ru.magicvolley.enums.CampType;
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
    public ApiResponse<List<CampDtoForList>> getAll() {
        return new ApiResponse<>(campService.getAll());
    }

    @GetMapping("/long-all")
    public ApiResponse<List<CampDtoForList>> getLongAll() {
        return new ApiResponse<>(campService.getAll(CampType.LONG));
    }

    @GetMapping("/short-all")
    public ApiResponse<List<CampDtoForList>> getShortAll() {
        return new ApiResponse<>(campService.getAll(CampType.SHORT));
    }

    @GetMapping("/{id}")
    public ApiResponse<CampDto> getById(@PathVariable UUID id) {
        return new ApiResponse<>(campService.getById(id));
    }

    @PostMapping("/short")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> createShort(@RequestBody CampDto camp) {
        return new ApiResponse<>(campService.create(camp, CampType.SHORT));
    }

    @PostMapping("/long")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> createLong(@RequestBody CampDto camp) {
        return new ApiResponse<>(campService.create(camp, CampType.LONG));
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

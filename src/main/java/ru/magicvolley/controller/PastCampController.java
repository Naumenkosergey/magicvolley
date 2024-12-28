package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.CampDtoForList;
import ru.magicvolley.request.PastCampForUpdate;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.PastCampService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/magicvolley/past-camps", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PastCampController {

    private final PastCampService pastCampService;


    @GetMapping("/all")
    public ApiResponse<List<CampDtoForList>> getAll() {
        return new ApiResponse<>(pastCampService.getAll());
    }


    @PutMapping("/{campId}")
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<UUID> update(@RequestBody PastCampForUpdate pastCampForUpdate, @PathVariable UUID campId) {
        return new ApiResponse<>(pastCampService.update(pastCampForUpdate, campId));
    }

}

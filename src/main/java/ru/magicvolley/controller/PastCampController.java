package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.CampDto;
import ru.magicvolley.dto.CampDtoForList;
import ru.magicvolley.dto.PastCampDto;
import ru.magicvolley.enums.CampType;
import ru.magicvolley.request.PastCampForUpdate;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.CampService;
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

    @GetMapping("/{id}")
    public ApiResponse<PastCampDto> getById(@PathVariable UUID id) {
        return new ApiResponse<>(pastCampService.getById(id));
    }

    @PutMapping("/{campId}")
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<UUID> update(@RequestBody PastCampForUpdate pastCampForUpdate, @PathVariable UUID campId) {
        return new ApiResponse<>(pastCampService.update(pastCampForUpdate, campId));
    }

//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ApiResponse<Boolean> delete(@PathVariable UUID id) {
//        return new ApiResponse<>(campPastService.delete(id));
//    }
}

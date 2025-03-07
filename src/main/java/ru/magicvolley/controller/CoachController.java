package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.CoachDto;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.enums.CoachType;
import ru.magicvolley.request.CoachRequest;
import ru.magicvolley.response.CoachResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.CoachService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/magicvolley/coaches")
@AllArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @GetMapping("/all/classic-type")
    public ApiResponse<List<CoachDto>> getAllClassicType(){
        return new ApiResponse<>(coachService.getAll(CoachType.CLASSIC));
    }

    @GetMapping("/all/beach-type")
    public ApiResponse<List<CoachDto>> getAllBeachType(){
        return new ApiResponse<>(coachService.getAll(CoachType.BEACH));
    }

    @GetMapping("/all")
    public ApiResponse<List<CoachDto>> getAll(){
        return new ApiResponse<>(coachService.getAll());
    }

    @GetMapping("/all/media")
    public ApiResponse<List<MediaStorageInfo>> getAllMediaCoaches(){
        return new ApiResponse<>(coachService.getAllMediaCoaches(CoachType.BEACH, CoachType.CLASSIC));
    }

    @GetMapping("/all/media/beach")
    public ApiResponse<List<MediaStorageInfo>> getAllMediaCoachesBeach(){
        return new ApiResponse<>(coachService.getAllMediaCoaches(CoachType.BEACH));
    }

    @GetMapping("/all/media/classic")
    public ApiResponse<List<MediaStorageInfo>> getAllMediaCoachesClassic(){
        return new ApiResponse<>(coachService.getAllMediaCoaches(CoachType.CLASSIC));
    }

    @GetMapping("/{id}")
    public ApiResponse<CoachResponse> getById(@PathVariable UUID id){
        return new ApiResponse<>(coachService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> create(@RequestBody CoachRequest coach){
        return new ApiResponse<>(coachService.create(coach));
    }

    @PutMapping("/undefined")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> createUndefined(@RequestBody CoachRequest coach){
        return new ApiResponse<>(coachService.create(coach));
    }

    @PutMapping("/{coachId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> update(@RequestBody CoachRequest coach, @PathVariable UUID coachId){
        return new ApiResponse<>(coachService.update(coach, coachId));
    }

    @DeleteMapping("/{coachId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean>delete(@PathVariable UUID coachId){
        return new ApiResponse<>(coachService.delete(coachId));
    }

    @PutMapping("/visible/{coachId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> updateCoachVisibility(@RequestBody Boolean isVisible, @PathVariable UUID coachId){
        return new ApiResponse<>(coachService.updateCoachVisibility(isVisible, coachId));
    }

}

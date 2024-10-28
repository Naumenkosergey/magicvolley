package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.CoachDto;
import ru.magicvolley.request.CoachRequest;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.CoachService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/magicvolley/coaches")
@AllArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<List<CoachDto>> getAll(){
        return new ApiResponse<>(coachService.getAll());
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<CoachDto> getById(@PathVariable UUID id){
        return new ApiResponse<>(coachService.getById(id));
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> create(@RequestBody CoachRequest coach){
        return new ApiResponse<>(coachService.create(coach));
    }

    @PutMapping("/{coachId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> update(@RequestBody CoachDto coach, @PathVariable UUID coachId){
        return new ApiResponse<>(coachService.update(coach, coachId));
    }

    @DeleteMapping("/{coachId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean>delete(@PathVariable UUID coachId){
        return new ApiResponse<>(coachService.delete(coachId));
    }
}

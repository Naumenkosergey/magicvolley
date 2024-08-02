package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.entity.CoachEntity;
import ru.magicvolley.request.CoachRequest;
import ru.magicvolley.dto.CoachDto;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.CoachService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/coaches")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<CoachEntity> create(@RequestBody CoachRequest coach){
        return new ApiResponse<>(coachService.create(coach));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")

    public ApiResponse<CoachEntity> update(@RequestBody CoachEntity coach){
        return new ApiResponse<>(coachService.update(coach));
    }

    @DeleteMapping("/{coachId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpStatus delete(@PathVariable UUID coachId){
        coachService.delete(coachId);
        return HttpStatus.OK;
    }
}

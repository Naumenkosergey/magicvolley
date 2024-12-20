package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.ScheduleDto;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.ScheduleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/magicvolley/shedule")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService sheduleService;

    @GetMapping()
    public ApiResponse<List<ScheduleDto>> getSchedule() {

        return new ApiResponse<>(sheduleService.getSchedule());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> create(@RequestBody ScheduleDto request) {
        return new ApiResponse<>(sheduleService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    public ApiResponse<UUID> update(@RequestBody ScheduleDto request) {
        return new ApiResponse<>(sheduleService.update(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    public ApiResponse<ScheduleDto> getById(@PathVariable UUID id) {
        return new ApiResponse<>(sheduleService.getById(id));
    }

    @DeleteMapping("/trein/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean> delete(@PathVariable UUID id) {
        return new ApiResponse<>(sheduleService.delete(id));
    }
}

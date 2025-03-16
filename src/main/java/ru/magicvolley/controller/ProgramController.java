package ru.magicvolley.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.request.ProgramRequest;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.ProgramService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/magicvolley/camp/programs", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> create(@RequestBody ProgramRequest request) {
        return new ApiResponse<>(programService.createProgram(request));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<UUID> update(@RequestBody ProgramRequest programRequest) {
        return new ApiResponse<>(programService.updateProgram(programRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean> delete(@PathVariable UUID id) {
        return new ApiResponse<>(programService.delete(id));
    }


}

package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.ProfileDto;
import ru.magicvolley.entity.ProfileEntity;
import ru.magicvolley.request.PasswordUpdateForProfile;
import ru.magicvolley.request.ProfileForUpdate;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.ProfileService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/magicvolley/profiles")
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<ProfileDto>> getAll(){
        return new ApiResponse(profileService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<ProfileDto> getById(@PathVariable UUID id){
        return new ApiResponse<>(profileService.getById(id));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<UUID> update(@RequestBody ProfileForUpdate profile){
        return new ApiResponse<>(profileService.update(profile));
    }

    @PutMapping("/update-password")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<ProfileEntity> updatePassword(@RequestBody PasswordUpdateForProfile passwordUpdateForProfile){
        return new ApiResponse<>(profileService.updatePassword(passwordUpdateForProfile));
    }
}

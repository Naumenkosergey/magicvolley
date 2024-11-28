package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.UserDto;
import ru.magicvolley.entity.UserEntity;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/magicvolley/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<UserDto>> getAll(){
        return new ApiResponse(userService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('MODERATOR')")
    public ApiResponse<UserDto> getById(@PathVariable UUID id){
        return new ApiResponse<>(userService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> create(@RequestBody UserDto user){
        return new ApiResponse<>(userService.create(user));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<UserEntity> update(@RequestBody UserEntity user){
        return new ApiResponse<>(userService.update(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpStatus delete(@PathVariable UUID id){
        userService.delete(id);
        return HttpStatus.OK;
    }
}

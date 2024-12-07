package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.request.AddUserRequest;
import ru.magicvolley.response.UserForAdminResponse;
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
    public ApiResponse<List<UserForAdminResponse>> getAll(){
        return new ApiResponse(userService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UserForAdminResponse> getById(@PathVariable UUID id){
        return new ApiResponse<>(userService.getById(id));
    }

    @PostMapping("/add-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> create(@RequestBody AddUserRequest user){
        return new ApiResponse<>(userService.create(user));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<UUID> update(@RequestBody AddUserRequest user){
        return new ApiResponse<>(userService.update(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpStatus delete(@PathVariable UUID id){
        userService.delete(id);
        return HttpStatus.OK;
    }
}

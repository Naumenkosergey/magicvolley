package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magicvolley.entity.RoleEntity;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.RoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/all")
    public ApiResponse<List<RoleEntity>> getAll(){
        return new ApiResponse<>(roleService.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<RoleEntity> getById(@PathVariable UUID id){
        return new ApiResponse<>(roleService.getById(id));
    }

//    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ApiResponse<RoleEntity> create(@RequestBody UserDto user){
//        return new ApiResponse<>(roleService.create(user));
//    }
//
//    @PutMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<UserEntity> update(@RequestBody UserEntity user){
//        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public HttpStatus delete(@PathVariable UUID id){
//        userService.delete(id);
//        return HttpStatus.OK;
//    }
}

package com.example.demomv.controller;

import com.example.demomv.dto.UserDto;
import com.example.demomv.entity.CampEntity;
import com.example.demomv.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demomv.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAll(){
        return mapResponseEntity(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getAll(@PathVariable UUID id){
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserEntity> create(@RequestBody UserDto user){
        return new ResponseEntity<>(userService.create(user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserEntity> update(@RequestBody UserEntity user){
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable UUID id){
        userService.delete(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<List<UserEntity>> mapResponseEntity(List<UserEntity> users) {
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}

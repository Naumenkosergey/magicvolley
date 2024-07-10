package com.example.demomv.controller;

import com.example.demomv.dto.CoachDto;
import com.example.demomv.dto.UserDto;
import com.example.demomv.entity.CampEntity;
import com.example.demomv.entity.CoachEntity;
import com.example.demomv.entity.UserEntity;
import com.example.demomv.service.CoachService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/coaches")
@AllArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @GetMapping("/all")
    public ResponseEntity<List<CoachEntity>> getAll(){
        return mapResponseEntity(coachService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachEntity> getAll(@PathVariable UUID id){
        return new ResponseEntity<>(coachService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CoachEntity> create(@RequestBody CoachDto coach){
        return new ResponseEntity<>(coachService.create(coach), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CoachEntity> update(@RequestBody CoachEntity coach){
        return new ResponseEntity<>(coachService.update(coach), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable UUID id){
        coachService.delete(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<List<CoachEntity>> mapResponseEntity(List<CoachEntity> coaches) {
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    private ResponseEntity<CoachEntity> mapResponseEntity(CoachEntity coach) {
        return new ResponseEntity<>(coach, HttpStatus.OK);
    }
}

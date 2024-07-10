package com.example.demomv.controller;

import com.example.demomv.dto.CampDto;
import com.example.demomv.entity.CampEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demomv.service.CampService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/camps")
@AllArgsConstructor
public class CampController {

    private final CampService campService;

    @GetMapping("/all")
    public ResponseEntity<List<CampDto>> getAll(){
        return mapResponseEntity(campService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampDto> getAll(@PathVariable UUID id){
        return mapResponseEntity(campService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CampEntity> create(@RequestBody CampDto camp){
        return mapResponseEntity(campService.create(camp));
    }

    @PutMapping
    public ResponseEntity<CampEntity> update(@RequestBody CampEntity camp){
        return mapResponseEntity(campService.update(camp));
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable UUID id){
        campService.delete(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<List<CampDto>> mapResponseEntity(List<CampDto> camps) {
        return new ResponseEntity<>(camps, HttpStatus.OK);
    }

    private ResponseEntity<CampEntity> mapResponseEntity(CampEntity camp) {
        return new ResponseEntity<>(camp, HttpStatus.OK);
    }

    private ResponseEntity<CampDto> mapResponseEntity(CampDto camp) {
        return new ResponseEntity<>(camp, HttpStatus.OK);
    }
}

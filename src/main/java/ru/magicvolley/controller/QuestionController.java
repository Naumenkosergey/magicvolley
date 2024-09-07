package ru.magicvolley.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.request.QuestionRequest;
import ru.magicvolley.response.QuestionResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.QuestionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/magicvolley/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/all")
    public ApiResponse<List<QuestionResponse>> getAll() {

        return new ApiResponse<>(questionService.getAll());
    }

    @GetMapping("/{questionId}")
//    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<QuestionResponse> getById(@PathVariable UUID questionId){
        return new ApiResponse<>(questionService.getById(questionId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> create(@RequestBody QuestionRequest questionRequest){
        return new ApiResponse<>(questionService.create(questionRequest));
    }

    @PutMapping("/{questionId}")
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ApiResponse<UUID> update(@RequestBody QuestionRequest questionRequest){
        return new ApiResponse<>(questionService.update(questionRequest));
    }

    @DeleteMapping("/{questionId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> delete(@PathVariable UUID questionId){
        return new ApiResponse<>(questionService.delete(questionId));
    }
}

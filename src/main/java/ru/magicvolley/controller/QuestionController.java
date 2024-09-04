package ru.magicvolley.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magicvolley.response.QuestionResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/all")
    public ApiResponse<List<QuestionResponse>> getAll() {

        return new ApiResponse<>(questionService.getAll());
    }
}

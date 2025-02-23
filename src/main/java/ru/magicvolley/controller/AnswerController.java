package ru.magicvolley.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magicvolley.dto.AnswerDto;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.AnswerService;

@RestController
@RequestMapping("/magicvolley/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/send")
    public ApiResponse<Boolean> makeAnswer(@RequestBody AnswerDto answerDto) {

        return new ApiResponse<>(answerService.makeAnswer(answerDto));
    }
}

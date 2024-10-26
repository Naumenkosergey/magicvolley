package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magicvolley.response.ScheduleResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/magicvolley/shedule")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService sheduleService;

    @GetMapping()
    public ApiResponse<List<ScheduleResponse>> getSchedule() {

        return new ApiResponse<>(sheduleService.getSchedule());
    }

//    @GetMapping("/{questionId}")
////    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
//    public ApiResponse<QuestionResponse> getById(@PathVariable UUID questionId){
//        return new ApiResponse<>(questionService.getById(questionId));
//    }

//    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ApiResponse<UUID> create(@RequestBody QuestionRequest questionRequest){
//        return new ApiResponse<>(questionService.create(questionRequest));
//    }
//
//    @PutMapping("/{questionId}")
//    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
//    public ApiResponse<UUID> update(@RequestBody QuestionRequest questionRequest){
//        return new ApiResponse<>(questionService.update(questionRequest));
//    }
//
//    @DeleteMapping("/{questionId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ApiResponse<UUID> delete(@PathVariable UUID questionId){
//        return new ApiResponse<>(questionService.delete(questionId));
//    }
}

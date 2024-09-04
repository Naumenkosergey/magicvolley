package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.magicvolley.response.HomePageResponse;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final CoachService coachService;
    private final CampService campService;
    private final MediaService mediaService;
    private final QuestionService questionService;


    public HomePageResponse getHome() {

        return  HomePageResponse.builder()
                .coaches(coachService.getAll())
                .camps(campService.getAll())
                .medias(mediaService.getAllFiles())
                .questions(questionService.getAll())
                .build();
    }
}

package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.entity.AboutUsPageEntity;
import ru.magicvolley.enums.TypeEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.AboutUsPageRepository;
import ru.magicvolley.request.AboutUsRequest;
import ru.magicvolley.response.AboutUsResponse;

@Service
@RequiredArgsConstructor
public class AboutUsPageService {

    private final AboutUsPageRepository aboutUsPageRepository;
    private final MasterService masterService;
    private final ReviewService reviewService;
    private final ActivityService activityService;
    private final MediaService mediaService;


    @Transactional(readOnly = true)
    public AboutUsResponse getAbout() {
        AboutUsPageEntity aboutUsPageEntity = aboutUsPageRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Страница о нас не найдена"));

        return AboutUsResponse.builder()
                .title(aboutUsPageEntity.getTitle())
                .subTitleFirst(aboutUsPageEntity.getSubtitleFirst())
                .subTitleSecond(aboutUsPageEntity.getSubtitleSecond())
                .subTitleThird(aboutUsPageEntity.getSubtitleThird())
                .numberOfCamps(aboutUsPageEntity.getNumberOfCamps())
                .numberOfStudents(aboutUsPageEntity.getNumberOfStudents())
                .numberOfWorkouts(aboutUsPageEntity.getNumberOfWorkouts())
                .Activities(activityService.getActivities())
                .reviews(reviewService.getReviews())
                .master(masterService.getMaster())
                .gallery(mediaService.getAllImages(TypeEntity.PAST_GALLERY))
                .build();

    }

    @Transactional
    public Boolean update(AboutUsRequest aboutUsRequest) {
        AboutUsPageEntity aboutUsPageFromDb = aboutUsPageRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Страница о нас не найдена"));

        aboutUsPageFromDb.setTitle(aboutUsRequest.getTitle());
        aboutUsPageFromDb.setSubtitleFirst(aboutUsRequest.getSubTitleFirst());
        aboutUsPageFromDb.setSubtitleSecond(aboutUsRequest.getSubTitleSecond());
        aboutUsPageFromDb.setSubtitleThird(aboutUsRequest.getSubTitleThird());
        aboutUsPageFromDb.setNumberOfCamps(aboutUsRequest.getNumberOfCamps());
        aboutUsPageFromDb.setNumberOfStudents(aboutUsRequest.getNumberOfStudents());
        aboutUsPageFromDb.setNumberOfWorkouts(aboutUsRequest.getNumberOfWorkouts());

        masterService.setMaster(aboutUsRequest.getMaster());

        return true;
    }
}

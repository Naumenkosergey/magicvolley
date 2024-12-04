package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.entity.AboutUsPageEntity;
import ru.magicvolley.repository.AboutUsPageRepository;
import ru.magicvolley.request.AboutUsRequest;
import ru.magicvolley.response.AboutUsResponse;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AboutUsPageService {

    private final AboutUsPageRepository aboutUsPageRepository;
    private final MasterService masterService;
    private final ReviewService reviewService;
    private final ActivityService activityService;



    @Transactional(readOnly = true)
    public AboutUsResponse getAbout() {
        AboutUsPageEntity aboutUsPageEntity = aboutUsPageRepository.findAll().stream().findFirst().orElse(null);

        return AboutUsResponse.builder()
                .title(Objects.nonNull(aboutUsPageEntity) ? aboutUsPageEntity.getTitle() : "")
                .subTitleFirst(Objects.nonNull(aboutUsPageEntity) ? aboutUsPageEntity.getSubtitleFirst() : "")
                .subTitleSecond(Objects.nonNull(aboutUsPageEntity) ? aboutUsPageEntity.getSubtitleSecond() : "")
                .Activities(activityService.getActivities())
                .reviews(reviewService.getReviews())
                .master(masterService.getMaster())
                .build();

    }

    @Transactional
    public Boolean update(AboutUsRequest aboutUsRequest) {
        AboutUsPageEntity aboutUsPageFromDb = aboutUsPageRepository.findAll().stream().findFirst().orElse(null);

        aboutUsPageFromDb.setTitle(aboutUsRequest.getTitle());
        aboutUsPageFromDb.setSubtitleFirst(aboutUsRequest.getSubTitleFirst());
        aboutUsPageFromDb.setSubtitleSecond(aboutUsRequest.getSubTitleSecond());

        masterService.setMaster(aboutUsRequest.getMaster());
        activityService.setActivities(aboutUsRequest.getActivities());
        reviewService.setReviews(aboutUsRequest.getReviews());

        return true;
    }
}

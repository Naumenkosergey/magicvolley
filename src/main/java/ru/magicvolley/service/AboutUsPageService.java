package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.AboutUsPageEntity;
import ru.magicvolley.entity.ActivityEntity;
import ru.magicvolley.entity.MasterEntity;
import ru.magicvolley.entity.ReviewEntity;
import ru.magicvolley.repository.AboutUsPageRepository;
import ru.magicvolley.repository.ActivityRepository;
import ru.magicvolley.repository.MasterRepository;
import ru.magicvolley.repository.ReviewRepository;
import ru.magicvolley.response.AboutResponse;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AboutUsPageService {

    private final AboutUsPageRepository aboutUsPageRepository;
    private final MasterRepository masterRepository;
    private final ActivityRepository activityRepository;
    private final ReviewRepository reviewRepository;
    private final MediaService mediaService;
    @Value("${media.prefix.url}")
    private String prefixUrlMedia;


    @Transactional(readOnly = true)
    public AboutResponse getAbout() {
        AboutUsPageEntity aboutUsPageEntity = aboutUsPageRepository.findAll().stream().findFirst().orElse(null);

        return AboutResponse.builder()
                .title(Objects.nonNull(aboutUsPageEntity) ? aboutUsPageEntity.getTitle() : "")
                .subTitleFirst(Objects.nonNull(aboutUsPageEntity) ? aboutUsPageEntity.getSubtitleFirst() : "")
                .subTitleSecond(Objects.nonNull(aboutUsPageEntity) ? aboutUsPageEntity.getSubtitleSecond() : "")
                .Activities(getActivities())
                .reviews(getReviews())
                .master(getMaster())
                .build();

    }

    private List<AboutResponse.Review> getReviews() {
        List<ReviewEntity> reviews = reviewRepository.findAll().stream()
                .sorted((o1, o2) -> o2.getDateReview().compareTo(o1.getDateReview()))
                .toList();

        return reviews.stream().map(reviewEntity ->
                AboutResponse.Review.builder()
                        .name(reviewEntity.getNameReviewer())
                        .image(new MediaStorageInfo(reviewEntity.getAvatarReviewer(), prefixUrlMedia))
                        .date(reviewEntity.getDateReview().toString())
                        .comment(reviewEntity.getReviewText())
                        .build()
        ).toList();
    }

    private List<AboutResponse.Activity> getActivities() {
        Map<UUID, ActivityEntity> mapActivityToId = activityRepository.findAll().stream()
                .collect(Collectors.toMap(ActivityEntity::getId, Function.identity()));
        Map<UUID, List<MediaStorageInfo>> allImagesForEntityIds = mediaService.getAllImagesForEntityIds(mapActivityToId.keySet());

        return mapActivityToId.entrySet().stream().map(entry ->
                AboutResponse.Activity.builder()
                        .name(entry.getValue().getTitle())
                        .images(collection(allImagesForEntityIds.get(entry.getKey())))
                        .build()
        ).toList();
    }

    private List<MediaStorageInfo> collection(Collection<MediaStorageInfo> collection) {
        if(CollectionUtils.isNotEmpty(collection)){
            return collection.stream().toList();
        }
        return Collections.emptyList();
    }

    private AboutResponse.Master getMaster() {
        MasterEntity masterEntity = masterRepository.findAll().stream().findFirst().orElse(null);
        if (Objects.nonNull(masterEntity)) {
            return AboutResponse.Master.builder()
                    .name(masterEntity.getName_master())
                    .image(new MediaStorageInfo(masterEntity.getAvatarMaster(), prefixUrlMedia))
                    .infos(Arrays.stream(masterEntity.getInfo().split(";")).toList())
                    .build();
        }
        return null;

    }
}

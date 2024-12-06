package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.ActivityEntity;
import ru.magicvolley.enums.TypeEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.ActivityRepository;
import ru.magicvolley.request.AboutUsRequest;
import ru.magicvolley.response.AboutUsResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final MediaService mediaService;
    @Value("${media.prefix.url}")
    private String prefixUrlMedia;

    public List<AboutUsResponse.Activity> getActivities() {
        Map<UUID, ActivityEntity> mapActivityToId = activityRepository.findAll().stream()
                .collect(Collectors.toMap(ActivityEntity::getId, Function.identity()));
        Map<UUID, List<MediaStorageInfo>> allImagesForEntityIds = mediaService.getAllImagesForEntityIds(mapActivityToId.keySet());

        return mapActivityToId.entrySet().stream().map(entry ->
                AboutUsResponse.Activity.builder()
                        .name(entry.getValue().getTitle())
                        .images(mediaService.getCollection(allImagesForEntityIds.get(entry.getKey())))
                        .build()
        ).toList();
    }

    @Transactional
    public Boolean updateActivate(AboutUsRequest.Activity activityRequest, UUID activityId) {
        ActivityEntity activityFromDb = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException(ActivityEntity.class, activityId));

        activityFromDb.setTitle(activityRequest.getName());
        activityRequest.getImages().forEach(image ->
                mediaService.mediaInfoToMediaStorage(image, activityFromDb.getId()));

        return true;
    }

    @Transactional
    public UUID createActivity(AboutUsRequest.Activity activityRequest) {

        ActivityEntity activityNew = ActivityEntity.builder()
                .id(UUID.randomUUID())
                .title(activityRequest.getName())
                .build();

        activityRepository.save(activityNew);

        activityRequest.getImages().forEach(image ->
                mediaService.mediaInfoToMediaStorage(image, activityNew.getId()));

        return activityNew.getId();
    }

    @Transactional
    public Boolean delete(UUID activityId) {
        ActivityEntity activityFomDb = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException(ActivityEntity.class, activityId));
        mediaService.delete(activityFomDb.getId(), TypeEntity.ACTIVATE);
        activityRepository.delete(activityFomDb);
        return true;
    }
}

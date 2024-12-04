package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.ActivityEntity;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.ActivityRepository;
import ru.magicvolley.request.AboutUsRequest;
import ru.magicvolley.response.AboutUsResponse;

import java.util.ArrayList;
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

    public void setActivities(List<AboutUsRequest.Activity> activityRequests) {

        List<ActivityEntity> activityEntities = activityRequests.stream()
                .map(activity -> {
                    UUID id = UUID.randomUUID();
                    return ActivityEntity.builder()
                            .id(id)
                            .title(activity.getName())
                            .build();
                }).toList();
        Map<String, UUID> activityCodeToId = activityEntities.stream().collect(Collectors.toMap(ActivityEntity::getTitle, ActivityEntity::getId));
        activityRepository.saveAll(activityEntities);
        List<MediaStorageEntity> mediaStorageEntities = new ArrayList<>();
        activityRequests.forEach(activity ->
                activity.getImages().forEach(image ->
                        mediaStorageEntities.add(mediaService.mediaInfoToMediaStorage(image, activityCodeToId.get(activity.getName()))))
        );
    }

    @Transactional
    public Boolean updateActivate(AboutUsRequest.Activity activityRequest) {
        ActivityEntity activityFromDb = activityRepository.findByTitle(activityRequest.getName())
                .orElseThrow(() -> new EntityNotFoundException(ActivityEntity.class, activityRequest.getName()));

        activityFromDb.setTitle(activityRequest.getName());
        activityRequest.getImages().forEach(image ->
                mediaService.mediaInfoToMediaStorage(image, activityFromDb.getId()));

        return true;
    }

}

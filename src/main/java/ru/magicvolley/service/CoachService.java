package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.Util;
import ru.magicvolley.dto.CoachDto;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.CampCoachEntity;
import ru.magicvolley.entity.CoachEntity;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.enums.CoachType;
import ru.magicvolley.enums.TypeEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampCoachRepository;
import ru.magicvolley.repository.CoachRepository;
import ru.magicvolley.request.CoachRequest;
import ru.magicvolley.response.CoachResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;
    private final CampCoachRepository campCoachRepository;
    private final MediaService mediaService;
    @Value("${media.prefix.url}")
    private String prefixUrlMedia;

    @Transactional
    public List<CoachDto> getAll(CoachType... types) {
        return coachRepository.findAllByCoachTypeLike(Util.getLike(types)).stream()
                .filter(coach -> Util.isAdminCurrentUser() || Objects.equals(Util.getOrDefaultIfNull(coach.isVisible(), Boolean.TRUE), Boolean.TRUE))
                .sorted(Comparator.comparing(CoachEntity::getCreatedAt))
                .map(x -> new CoachDto(x, prefixUrlMedia))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CoachDto> getAll() {
        return coachRepository.findAll().stream()
                .filter(coach -> Util.isAdminCurrentUser() || Objects.equals(Util.getOrDefaultIfNull(coach.isVisible(), Boolean.TRUE), Boolean.TRUE))
                .sorted(Comparator.comparing(CoachEntity::getCreatedAt))
                .map(x -> new CoachDto(x, prefixUrlMedia))
                .collect(Collectors.toList());
    }

    @Transactional
    public CoachResponse getById(UUID id) {
        CoachEntity coach = coachRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CoachEntity.class, id));
        List<MediaStorageInfo> allImages = mediaService.getAllImages(coach.getId(), TypeEntity.COACH);
        return new CoachResponse(coach, prefixUrlMedia, allImages);
    }

    @Transactional
    public UUID create(CoachRequest coach) {

        CoachEntity coachEntity = coachRepository.save(CoachEntity.builder()
                .id(UUID.randomUUID())
                .coachName(coach.getName())
                .info(String.join(";", coach.getInfos()))
                .coachType(getCoachTypeFromRequest(coach.getIsBeach(), coach.getIsClassic()))
                .promo(coach.getPromo())
                .isVisible(Boolean.TRUE)
                .build());
        MediaStorageEntity mainImage = mediaService.mediaInfoToMediaStorage(coach.getMainImage(), coachEntity.getId(), TypeEntity.COACH_AVATAR);
        setAvatar(mainImage, coachEntity);
        coachRepository.saveAndFlush(coachEntity);
        if (CollectionUtils.isNotEmpty(coach.getImages())) {
            coach.getImages().forEach(x -> mediaService.mediaInfoToMediaStorage(x, coachEntity.getId(), TypeEntity.COACH));
        }
        return coachEntity.getId();
    }

    @Transactional
    public UUID update(CoachRequest coach, UUID coachId) {

        CoachEntity coachFromDb = coachRepository.findById(coachId)
                .orElseThrow(() -> new EntityNotFoundException(CoachEntity.class, coach.getId()));
        coachFromDb.setCoachName(coach.getName());
        coachFromDb.setInfo(String.join(";", coach.getInfos()));
        coachFromDb.setPromo(coach.getPromo());
        coachFromDb.setCoachType(getCoachTypeFromRequest(coach.getIsBeach(), coach.getIsClassic()));

        MediaStorageEntity mainImage = mediaService.mediaInfoToMediaStorage(coach.getMainImage(), coachFromDb.getId(), TypeEntity.COACH_AVATAR);
        setAvatar(mainImage, coachFromDb);
        coachRepository.save(coachFromDb);
        replaceCoachImages(coach, mainImage, coachFromDb);
        return coachFromDb.getId();
    }

    private void replaceCoachImages(CoachRequest coach, MediaStorageEntity mainImage, CoachEntity coachFromDb) {
        List<MediaStorageInfo> coachImagesFromRequest = coach.getImages().stream()
                .filter(x -> !x.getId().equals(mainImage.getId()))
                .collect(Collectors.toList());
        mediaService.deletedOldImagesUploadNewImages(coachImagesFromRequest, coachFromDb.getId(), TypeEntity.COACH);
    }

    private static void setAvatar(MediaStorageEntity mediaStorage, CoachEntity coachEntity) {
        if (Objects.nonNull(mediaStorage)) {
            coachEntity.setAvatar(mediaStorage);
        }
    }

    @Transactional
    public Boolean delete(UUID coachId) {
        CoachEntity coachFomDb = coachRepository.findById(coachId)
                .orElseThrow(() -> new EntityNotFoundException(CoachEntity.class, coachId));
        List<CampCoachEntity> campCoaches = campCoachRepository.findAllByIdCoachId(coachId);
        campCoachRepository.deleteAll(campCoaches);
        coachRepository.delete(coachFomDb);
        mediaService.delete(coachFomDb.getAvatar());
        return true;
    }

    @Transactional(readOnly = true)
    public List<MediaStorageInfo> getAllMediaCoaches(CoachType... types) {

        Set<UUID> coachesIds = coachRepository.findAllByCoachTypeLike(Util.getLike(types)).stream()
                .map(CoachEntity::getId)
                .collect(Collectors.toSet());

        return Util.getSaveStream(mediaService.getAllImagesForEntityIds(coachesIds, TypeEntity.COACH).entrySet())
                .flatMap(x -> x.getValue().stream()).toList();
    }

    @Transactional
    public UUID updateCoachVisibility(Boolean isVisible, UUID coachId) {
        CoachEntity coachFromDb = coachRepository.findById(coachId)
                .orElseThrow(() -> new EntityNotFoundException(CoachEntity.class, coachId));
        coachFromDb.setVisible(Objects.nonNull(isVisible) ? isVisible : true);
        return coachFromDb.getId();
    }

    private String getCoachTypeFromRequest(Boolean isBeach, Boolean isClassic) {
        return Util.getOrDefaultIfNull(isBeach, Boolean.FALSE) && Util.getOrDefaultIfNull(isClassic, Boolean.FALSE)
                ? "BEACH;CLASSIC" : Util.getOrDefaultIfNull(isBeach, Boolean.FALSE)
                ? "BEACH" : Util.getOrDefaultIfNull(isClassic, Boolean.FALSE)
                ? "CLASSIC" : "BEACH;CLASSIC";
    }

}

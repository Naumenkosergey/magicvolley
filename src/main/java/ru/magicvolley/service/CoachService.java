package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
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
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampCoachRepository;
import ru.magicvolley.repository.CoachRepository;
import ru.magicvolley.request.CoachRequest;

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
                .sorted(Comparator.comparing(CoachEntity::getCreatedAt))
                .map(x -> new CoachDto(x, prefixUrlMedia))
                .collect(Collectors.toList());
    }

    @Transactional
    public CoachDto getById(UUID id) {
        return coachRepository.findById(id)
                .map(x -> new CoachDto(x, prefixUrlMedia))
                .orElseThrow(() -> new EntityNotFoundException(CoachEntity.class, id));
    }

    @Transactional
    public UUID create(CoachRequest coach) {

        CoachEntity coachEntity = coachRepository.save(CoachEntity.builder()
                .id(UUID.randomUUID())
                .coachName(coach.getName())
                .info(String.join(";", coach.getInfos()))
                .promo(coach.getPromo())
                .build());
        MediaStorageEntity mediaStorage = mediaService.mediaInfoToMediaStorage(coach.getMainImage(), coachEntity.getId());
        setAvatar(mediaStorage, coachEntity);
        coachRepository.save(coachEntity);
        return coachEntity.getId();
    }

    @Transactional
    public UUID update(CoachDto coach, UUID coachId) {

        CoachEntity coachFromDb = coachRepository.findById(coachId)
                .orElseThrow(() -> new EntityNotFoundException(CoachEntity.class, coach.getId()));
        coachFromDb.setCoachName(coach.getName());
        coachFromDb.setInfo(String.join(";", coach.getInfos()));
        coachFromDb.setPromo(coach.getPromo());

        MediaStorageEntity mediaStorage = mediaService.mediaInfoToMediaStorage(coach.getMainImage(), coachFromDb.getId());
        setAvatar(mediaStorage, coachFromDb);
        coachRepository.save(coachFromDb);
        return coachFromDb.getId();
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

        return Util.getSaveStream(mediaService.getAllImagesForEntityIds(coachesIds).entrySet())
                .flatMap(x -> x.getValue().stream()).toList();
    }
}

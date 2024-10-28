package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.CoachDto;
import ru.magicvolley.entity.CampCoachEntity;
import ru.magicvolley.entity.CoachEntity;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampCoachRepository;
import ru.magicvolley.repository.CoachRepository;
import ru.magicvolley.request.CoachRequest;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;
    private final CampCoachRepository campCoachRepository;
    private final MediaService mediaService;

    @Transactional
    public List<CoachDto> getAll() {
        return coachRepository.findAll().stream()
                .sorted(Comparator.comparing(CoachEntity::getCreatedAt))
                .map(CoachDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CoachDto getById(UUID id) {
        return coachRepository.findById(id)
                .map(CoachDto::new)
                .orElseThrow(() -> new EntityNotFoundException(CoachEntity.class, id));
    }

    @Transactional
    public UUID create(CoachRequest coach) {
        MediaStorageEntity avatar = null;
        if (Objects.nonNull(coach.getMainImage())) {
            try {
                avatar = mediaService.createMediaStorage(coach.getMainImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        CoachEntity coachEntity = coachRepository.save(CoachEntity.builder()
                .id(UUID.randomUUID())
                .coachName(coach.getName())
                .info(String.join(";", coach.getInfos()))
                .promo(coach.getPromo())
//                        .imageId(avatar.getId())
                .avatar(avatar)
                .build());
        return coachEntity.getId();
    }

    @Transactional
    public UUID update(CoachDto coach, UUID coachId) {
        CoachEntity coachFromDb = coachRepository.findById(coachId)
                .orElseThrow(() -> new EntityNotFoundException(CoachEntity.class, coach.getId()));
        coachFromDb.setCoachName(coach.getName());
        coachFromDb.setInfo(String.join(";", coach.getInfos()));
        coachFromDb.setPromo(coach.getPromo());

        coachRepository.save(coachFromDb);
        return coachFromDb.getId();
    }

    @Transactional
    public Boolean delete(UUID coachId) {
        CoachEntity coachFomDb = coachRepository.findById(coachId)
                .orElseThrow(() -> new EntityNotFoundException(CoachEntity.class, coachId));
        List<CampCoachEntity> campCoaches = campCoachRepository.findAllByIdCoachId(coachId);
        campCoachRepository.deleteAll(campCoaches);
        coachRepository.delete(coachFomDb);
        return true;
    }

    public List<CoachDto> getCouches(Collection<CoachEntity> coaches) {
        return coaches.stream().map(CoachDto::new)
                .collect(Collectors.toList());

    }
}

package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.entity.CampCoachEntity;
import ru.magicvolley.entity.CoachEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampCoachRepository;
import ru.magicvolley.repository.CoachRepository;
import ru.magicvolley.request.CoachRequest;
import ru.magicvolley.dto.CoachDto;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;
    private final CampCoachRepository campCoachRepository;

    @Transactional
    public List<CoachDto> getAll() {
        return coachRepository.findAll().stream()
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
    public CoachEntity create(CoachRequest coach) {
        return coachRepository.save(CoachEntity.builder()
                .id(UUID.randomUUID())
                .coachName(coach.getName())
                .surename(coach.getSurename())
                .info(coach.getInfos())
                .build());
    }

    @Transactional
    public CoachEntity update(CoachEntity coach) {
        CoachEntity coachFromDb = coachRepository.findById(coach.getId())
                .orElseThrow(() -> new RuntimeException("ERROR"));
        coachFromDb.setSurename(coach.getSurename());
        coachFromDb.setCoachName(coach.getCoachName());
        coachFromDb.setInfo(coach.getInfo());

        return coachRepository.save(coachFromDb);
    }

    @Transactional
    public void delete(UUID coachId) {
        CoachEntity coachFomDb = coachRepository.findById(coachId)
                .orElseThrow(() -> new RuntimeException("ERROR"));
        List<CampCoachEntity> campCoaches = campCoachRepository.findAllByIdCoachId(coachId);
        campCoachRepository.deleteAll(campCoaches);
        coachRepository.delete(coachFomDb);
    }


    public List<CoachDto> getCouches(Collection<CoachEntity> coaches) {
        return coaches.stream().map(CoachDto::new)
                .collect(Collectors.toList());

    }
}

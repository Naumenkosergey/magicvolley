package ru.magicvolley.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.CampDto;
import ru.magicvolley.dto.CoachDto;
import ru.magicvolley.entity.CampCoachEntity;
import ru.magicvolley.entity.CampEntity;
import ru.magicvolley.repository.CampCoachRepository;
import ru.magicvolley.repository.CampRepository;
import ru.magicvolley.repository.CoachRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampService {

    private final CampRepository campRepository;
    private final CoachRepository coachRepository;
    private final CampCoachRepository campCoachRepository;

    @Transactional
    public List<CampDto> getAll() {
        List<CampEntity> campEntities = campRepository.findAll();
        return campEntities.stream()
                .map(campEntity -> CampDto.builder()
                        .id(campEntity.getId())
                        .price(campEntity.getPrice())
                        .name(campEntity.getCampName())
                        .info(campEntity.getInfo())
                        .dateStart(campEntity.getDateStart())
                        .dateEnd(campEntity.getDateEnd())
                        .countFree(campEntity.getCountFree())
                        .countAll(campEntity.getCountAll())
                        .coaches(campEntity.getCoaches().stream()
                                .map(CoachDto::new)
                                .toList()
                        )
                        .build()).
                toList();
    }

    @Transactional
    public CampDto getById(UUID id) {
        CampEntity campEntity = campRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("не найден кемп по id " + id));
        return CampDto.builder()
                .id(id)
                .price(campEntity.getPrice())
                .name(campEntity.getCampName())
                .info(campEntity.getInfo())
                .dateStart(campEntity.getDateStart())
                .dateEnd(campEntity.getDateEnd())
                .countFree(campEntity.getCountFree())
                .countAll(campEntity.getCountAll())
//                .coaches(campEntity.getCampCoaches().stream().map(coach -> coach.getId().getCoachId()).toList())
                .build();

    }

    @Transactional
    public CampEntity create(CampDto camp) {

        CampEntity campEntity = CampEntity.builder()
                .id(UUID.randomUUID())
                .campName(camp.getName())
                .info(camp.getInfo())
                .dateStart(camp.getDateStart())
                .dateEnd(camp.getDateEnd())
                .price(camp.getPrice())
                .countAll(camp.getCountAll())
                .countFree(camp.getCountFree())
                .build();
        campRepository.saveAndFlush(campEntity);

        List<CampCoachEntity> campCoachEntities = camp.getCoaches().stream()
                .map(coach -> CampCoachEntity.builder()
                        .id(CampCoachEntity.Id.builder()
                                .campId(campEntity.getId())
                                .coachId(coach.getId())
                                .build())
                        .camp(campEntity)
                        .build())
                .toList();

        campCoachRepository.saveAll(campCoachEntities);
        return campEntity;
    }

    @Transactional
    public CampEntity update(CampEntity camp) {
        return campRepository.save(camp);
    }

    @Transactional
    public void delete(UUID campId) {
        CampEntity campFromDb = campRepository.findById(campId)
                .orElseThrow(() -> new RuntimeException("ERROR"));
        List<CampCoachEntity> campCoaches = campCoachRepository.findAllByIdCoachId(campId);
        campCoachRepository.deleteAll(campCoaches);
        campRepository.delete(campFromDb);
    }
}

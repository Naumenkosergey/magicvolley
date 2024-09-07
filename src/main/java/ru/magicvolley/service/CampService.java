package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.magicvolley.dto.CampDto;
import ru.magicvolley.dto.CoachDto;
import ru.magicvolley.entity.CampCoachEntity;
import ru.magicvolley.entity.CampEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampCoachRepository;
import ru.magicvolley.repository.CampRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampService {

    private final CampRepository campRepository;
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
                .coaches(campEntity.getCoaches().stream().map(CoachDto::new).toList())
                .build();

    }

    @Transactional
    public UUID create(CampDto camp) {

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

        createCampCoaches(camp.getCoaches(), campEntity);
//        campEntity.setCoaches(campCoaches);
        return campEntity.getId();
    }

    private void createCampCoaches(Collection<CoachDto> coaches, CampEntity campEntity) {
        if (!CollectionUtils.isEmpty(coaches)) {
            List<CampCoachEntity> campCoachEntities = coaches.stream()
                    .map(coach -> CampCoachEntity.builder()
                            .id(CampCoachEntity.Id.builder()
                                    .campId(campEntity.getId())
                                    .coachId(coach.getId())
                                    .build())
                            .camp(campEntity)
                            .build())
                    .toList();

            campCoachRepository.saveAll(campCoachEntities);
        }
//        return campCoachEntities;
    }

    @Transactional
    public UUID update(CampDto camp) {
        CampEntity campFromDb = campRepository.findById(camp.getId())
                .orElseThrow(() -> new EntityNotFoundException(CampEntity.class, camp.getId()));

        campFromDb.setCampName(camp.getName());
        campFromDb.setInfo(camp.getInfo());
        campFromDb.setDateStart(camp.getDateStart());
        campFromDb.setDateEnd(camp.getDateEnd());
        campFromDb.setPrice(camp.getPrice());
        campFromDb.setCountAll(camp.getCountAll());
        campFromDb.setCountFree(camp.getCountFree());

        replaceCampCoaches(camp.getCoaches(), campFromDb);
        campRepository.save(campFromDb);
        return campFromDb.getId();
    }

    private void replaceCampCoaches(List<CoachDto> coaches, CampEntity campFromDb) {
        List<CampCoachEntity> campCoaches = campCoachRepository.findAllByIdCampId(campFromDb.getId());
        if (!CollectionUtils.isEmpty(coaches)) {
            campCoachRepository.deleteAll(campCoaches);
        }
        createCampCoaches(coaches, campFromDb);
    }

    @Transactional
    public Boolean delete(UUID campId) {
        CampEntity campFromDb = campRepository.findById(campId)
                .orElseThrow(() -> new EntityNotFoundException(CampEntity.class, campId));
        List<CampCoachEntity> campCoaches = campCoachRepository.findAllByIdCoachId(campId);
        campCoachRepository.deleteAll(campCoaches);
        campRepository.delete(campFromDb);
        return true;
    }
}

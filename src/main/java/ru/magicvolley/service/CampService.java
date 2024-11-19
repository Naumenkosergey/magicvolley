package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.CampDto;
import ru.magicvolley.dto.CampPackageCardDto;
import ru.magicvolley.dto.CoachDto;
import ru.magicvolley.entity.CampCoachEntity;
import ru.magicvolley.entity.CampEntity;
import ru.magicvolley.entity.CampPackageCardEntity;
import ru.magicvolley.enums.CampType;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampCoachRepository;
import ru.magicvolley.repository.CampPackageCardRepository;
import ru.magicvolley.repository.CampRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampService {

    private final CampRepository campRepository;
    private final CampCoachRepository campCoachRepository;
    private final CampPackageCardRepository campPackageCardRepository;

    @Transactional
    public List<CampDto> getAll() {
        List<CampEntity> campEntities = campRepository.findAll();
        return campEntities.stream()
                .map(campEntity -> CampDto.builder()
                        .id(campEntity.getId())
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
                        .packages(campEntity.getPackages().stream()
                                .map(CampPackageCardDto::new)
                                .toList())
                        .build()).
                toList();
    }

    @Transactional
    public CampDto getById(UUID id) {
        CampEntity campEntity = campRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("не найден кемп по id " + id));
        return CampDto.builder()
                .id(id)
                .name(campEntity.getCampName())
                .info(campEntity.getInfo())
                .dateStart(campEntity.getDateStart())
                .dateEnd(campEntity.getDateEnd())
                .countFree(campEntity.getCountFree())
                .countAll(campEntity.getCountAll())
                .coaches(campEntity.getCoaches().stream().map(CoachDto::new).toList())
                .packages(campEntity.getPackages().stream().map(CampPackageCardDto::new).toList())
                .build();

    }

    @Transactional
    public UUID create(CampDto camp, CampType campType) {

        CampEntity campEntity = CampEntity.builder()
                .id(UUID.randomUUID())
                .campName(camp.getName())
                .info(camp.getInfo())
                .dateStart(camp.getDateStart())
                .dateEnd(camp.getDateEnd())
                .campType(campType)
                .countAll(camp.getCountAll())
                .countFree(camp.getCountFree())
                .build();
        campRepository.saveAndFlush(campEntity);

        createCampCoaches(camp.getCoaches(), campEntity);
        createCampPackages(camp.getPackages(), campEntity);
        return campEntity.getId();
    }

    private void createCampPackages(List<CampPackageCardDto> packages, CampEntity campEntity) {
        if (CollectionUtils.isNotEmpty(packages)) {
            List<CampPackageCardEntity> collect = packages.stream()
                    .map(pack -> createCampPackage(pack, campEntity))
                    .collect(Collectors.toList());
            campPackageCardRepository.saveAll(collect);
        }
    }

    private CampPackageCardEntity createCampPackage(CampPackageCardDto packageCard, CampEntity campEntity) {
        return CampPackageCardEntity.builder()
                .id(CampPackageCardEntity.Id.builder()
                        .campId(campEntity.getId())
                        .packageCardId(packageCard.getPackageId())
                        .build())
                .info(packageCard.getInfo())
                .totalPrice(packageCard.getTotalPrice())
                .bookingPrice(packageCard.getBookingPrice())
                .firstPrice(packageCard.getFirstPrice())
                .firstLimitation(packageCard.getFirstLimitation())
                .secondPrice(packageCard.getSecondPrice())
                .secondLimitation(packageCard.getSecondLimitation())
                .thirdPrice(packageCard.getThirdPrice())
                .thirdLimitation(packageCard.getThirdLimitation())
                .build();
    }

    private void createCampCoaches(Collection<CoachDto> coaches, CampEntity campEntity) {
        if (CollectionUtils.isNotEmpty(coaches)) {
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
    }

    @Transactional
    public UUID update(CampDto camp) {
        CampEntity campFromDb = campRepository.findById(camp.getId())
                .orElseThrow(() -> new EntityNotFoundException(CampEntity.class, camp.getId()));

        campFromDb.setCampName(camp.getName());
        campFromDb.setInfo(camp.getInfo());
        campFromDb.setDateStart(camp.getDateStart());
        campFromDb.setDateEnd(camp.getDateEnd());
        campFromDb.setCountAll(camp.getCountAll());
        campFromDb.setCountFree(camp.getCountFree());

        replaceCampCoaches(camp.getCoaches(), campFromDb);
        replaceCampPackages(camp.getPackages(), campFromDb);
        campRepository.save(campFromDb);
        return campFromDb.getId();
    }

    private void replaceCampCoaches(List<CoachDto> coaches, CampEntity campFromDb) {
        List<CampCoachEntity> campCoaches = campCoachRepository.findAllByIdCampId(campFromDb.getId());
        if (CollectionUtils.isNotEmpty(coaches)) {
            campCoachRepository.deleteAll(campCoaches);
        }
        createCampCoaches(coaches, campFromDb);
    }

    private void replaceCampPackages(List<CampPackageCardDto> campPackageCards, CampEntity campFromDb) {
        List<CampPackageCardEntity> campCards = campPackageCardRepository.findAllByIdCampId(campFromDb.getId());
        if (CollectionUtils.isNotEmpty(campCards)) {
            campPackageCardRepository.deleteAll(campCards);
        }
        createCampPackages(campPackageCards, campFromDb);
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

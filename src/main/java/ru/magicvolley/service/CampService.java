package ru.magicvolley.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.CampDto;
import ru.magicvolley.dto.CampPackageCardDto;
import ru.magicvolley.dto.CoachDto;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.CampCoachEntity;
import ru.magicvolley.entity.CampEntity;
import ru.magicvolley.entity.CampPackageCardEntity;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.enums.CampType;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampCoachRepository;
import ru.magicvolley.repository.CampPackageCardRepository;
import ru.magicvolley.repository.CampRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Data
public class CampService {

    private final CampRepository campRepository;
    private final CampCoachRepository campCoachRepository;
    private final CampPackageCardRepository campPackageCardRepository;
    private final CampUserService campUserService;
    private final MediaService mediaService;

    @Value("${media.prefix.url}")
    private String prefixUrlMedia;

    @Transactional(readOnly = true)
    public List<CampDto> getAll(CampType type) {
        List<CampEntity> campEntities = campRepository.findAllByCampType(type);
        return getList(campEntities);
    }

    @Transactional(readOnly = true)
    public List<CampDto> getAll() {
        List<CampEntity> campEntities = campRepository.findAll();
        return getList(campEntities);
    }

    public List<CampDto> getList(List<CampEntity> campEntities) {
        Set<UUID> ids = campEntities.stream().map(CampEntity::getId).collect(Collectors.toSet());
        Map<UUID, List<MediaStorageInfo>> allImagesForCamIds = mediaService.getAllImagesForCamIds(ids);
        return campEntities.stream()
                .map(campEntity -> buildCampDto(campEntity, allImagesForCamIds))
                .toList();
    }

    private CampDto buildCampDto(CampEntity campEntity, Map<UUID, List<MediaStorageInfo>> allImagesForCamIds) {
        List<MediaStorageInfo> mediaStorageInfos = allImagesForCamIds.get(campEntity.getId());
        mediaStorageInfos.removeIf(x -> Objects.equals(x.getId(), campEntity.getMainImage().getId())
                || Objects.equals(x.getId(), campEntity.getImageCart().getId()));
        return CampDto.builder()
                .id(campEntity.getId())
                .name(campEntity.getCampName())
                .info(campEntity.getInfo())
                .dateStart(campEntity.getDateStart())
                .dateEnd(campEntity.getDateEnd())
                .countFree(campEntity.getCountFree())
                .countAll(campEntity.getCountAll())
                .dateString(getDateString(campEntity.getDateStart(), campEntity.getDateEnd()))
                .coaches(campEntity.getCoaches().stream().map(x -> new CoachDto(x, prefixUrlMedia)).toList())
                .packages(campEntity.getPackages().stream().map(CampPackageCardDto::new).toList())
                .mainImage(new MediaStorageInfo(campEntity.getMainImage(), prefixUrlMedia))
                .imageCart(new MediaStorageInfo(campEntity.getImageCart(), prefixUrlMedia))
                .images(mediaStorageInfos)
                .build();
    }

    @Transactional(readOnly = true)
    public CampDto getById(UUID id) {
        CampEntity campEntity = campRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("не найден кемп по id " + id));
        Map<UUID, List<MediaStorageInfo>> allImagesForCamIds = mediaService.getAllImagesForCamIds(Set.of(campEntity.getId()));
        return buildCampDto(campEntity, allImagesForCamIds);
    }


    private String getDateString(LocalDate dateStart, LocalDate dateEnd) {
        String monthStartString = getMounthString(dateStart.getMonthValue());
        String monthEndString = getMounthString(dateEnd.getMonthValue());

        if (monthStartString.equals(monthEndString)) {
            return dateStart.getDayOfMonth() + "-" + dateEnd.getDayOfMonth() + " " + monthStartString;
        } else {
            return dateStart.getDayOfMonth() + " " + monthStartString + " - "
                    + dateEnd.getDayOfMonth() + " " + monthEndString;
        }
    }

    private String getMounthString(int month) {
        return switch (month) {
            case 1 -> "января";
            case 2 -> "февраля";
            case 3 -> "марта";
            case 4 -> "апряля";
            case 5 -> "мая";
            case 6 -> "июня";
            case 7 -> "июля";
            case 8 -> "августа";
            case 9 -> "сентября";
            case 10 -> "октября";
            case 11 -> "ноября";
            case 12 -> "декабря";
            default -> "";
        };
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

        MediaStorageEntity mainImage = mediaService.mediaInfoToMediaStorage(camp.getMainImage(), campEntity.getId());
        MediaStorageEntity imageCart = mediaService.mediaInfoToMediaStorage(camp.getImageCart(), campEntity.getId());
        setMainImage(mainImage, campEntity);
        setImageCart(imageCart, campEntity);
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

    private static void setMainImage(MediaStorageEntity mediaStorage, CampEntity campEntity) {
        if (Objects.nonNull(mediaStorage)) {
            campEntity.setMainImage(mediaStorage);
        }
    }

    private static void setImageCart(MediaStorageEntity mediaStorage, CampEntity campEntity) {
        if (Objects.nonNull(mediaStorage)) {
            campEntity.setImageCart(mediaStorage);
        }
    }
}

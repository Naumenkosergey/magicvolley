package ru.magicvolley.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.Util;
import ru.magicvolley.dto.*;
import ru.magicvolley.entity.CampCoachEntity;
import ru.magicvolley.entity.CampEntity;
import ru.magicvolley.entity.CampPackageCardEntity;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.enums.CampType;
import ru.magicvolley.enums.TypeEntity;
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
    public List<CampDtoForList> getAll(CampType type) {
        List<CampEntity> campEntities = campRepository.findAllByCampType(type);
        return getList(campEntities);
    }

    @Transactional(readOnly = true)
    public List<CampDtoForList> getAll() {
        List<CampEntity> campEntities = campRepository.findAll().stream()
                .sorted(Comparator.comparing(CampEntity::getDateStart)).toList();
        return getList(campEntities);
    }

    public List<CampDtoForList> getList(List<CampEntity> campEntities) {
//        Set<UUID> ids = campEntities.stream().map(CampEntity::getId).collect(Collectors.toSet());
//        Map<UUID, List<MediaStorageInfo>> allImagesForCamIds = mediaService.getAllImagesForCamIds(ids);
        return campEntities.stream()
                .map(this::buildCampDtoForList)
                .toList();
    }

    public List<CampDtoForList> getCampList(List<CampEntity> campEntities) {
        return campEntities.stream()
                .map(this::buildCampDtoForList)
                .toList();
    }

    private CampDto buildCampDto(CampEntity campEntity, Map<UUID, List<MediaStorageInfo>> allImagesForCamIds) {
        List<MediaStorageInfo> mediaStorageInfos = allImagesForCamIds.get(campEntity.getId());
        if (CollectionUtils.isNotEmpty(mediaStorageInfos)) {
            mediaStorageInfos.removeIf(x -> Objects.equals(x.getId(), campEntity.getMainImageId())
                    || Objects.equals(x.getId(), campEntity.getCartImageId()));
        }
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

    private CampDtoForList buildCampDtoForList(CampEntity campEntity) {
        return CampDtoForList.builder()
                .id(campEntity.getId())
                .name(campEntity.getCampName())
                .dateString(getDateString(campEntity.getDateStart(), campEntity.getDateEnd()))
                .imageCart(new MediaStorageInfo(campEntity.getImageCart(), prefixUrlMedia))
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
        String monthStartString = getMounthString(Objects.nonNull(dateStart) ? dateStart.getMonthValue() : 0);
        String monthEndString = getMounthString(Objects.nonNull(dateStart) ? dateEnd.getMonthValue() : 0);

        if (monthStartString.equals(monthEndString)) {
            return getMonth(dateStart) + "-" + getMonth(dateEnd) + " " + monthStartString;
        } else {
            return getMonth(dateStart) + " " + monthStartString + " - " + getMonth(dateEnd) + " " + monthEndString;
        }
    }

    private int getMonth(LocalDate date) {
        return Objects.nonNull(date) ? date.getDayOfMonth() : 0;
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
        loadImagesForCamp(camp.getImages(), campEntity);
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
    public UUID update(CampDto campRequest) {
        CampEntity campFromDb = campRepository.findById(campRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException(CampEntity.class, campRequest.getId()));

        campFromDb.setCampName(campRequest.getName());
        campFromDb.setInfo(campRequest.getInfo());
        campFromDb.setDateStart(campRequest.getDateStart());
        campFromDb.setDateEnd(campRequest.getDateEnd());
        campFromDb.setCountAll(campRequest.getCountAll());
        campFromDb.setCountFree(campRequest.getCountFree());


        replaceImageCart(campRequest.getImageCart(), campFromDb);
        replaceMainImage(campRequest.getMainImage(), campFromDb);
        replaceCampCoaches(campRequest.getCoaches(), campFromDb);
        replaceCampPackages(campRequest.getPackages(), campFromDb);
        loadImagesForCamp(campRequest.getImages(), campFromDb);
        campRepository.save(campFromDb);
        return campFromDb.getId();
    }

    private void loadImagesForCamp(List<MediaStorageInfo> images, CampEntity campFromDb) {
        Map<UUID, List<MediaStorageInfo>> allImagesForCamIds = mediaService.getAllImagesForCamIds(Set.of(campFromDb.getId()));

        Set<UUID> imagesRequestIds = Util.getSaveStream(images).map(MediaStorageInfo::getId).collect(Collectors.toSet());
        List<MediaStorageInfo> mediaStorageInfos = allImagesForCamIds.get(campFromDb.getId());
        if (CollectionUtils.isNotEmpty(mediaStorageInfos)) {
            mediaStorageInfos.removeIf(x -> Objects.equals(x.getId(), campFromDb.getMainImageId())
                    || Objects.equals(x.getId(), campFromDb.getCartImageId()));
            mediaStorageInfos.removeIf(x -> imagesRequestIds.contains(x.getId()));
        }
        Set<UUID> ids = Util.getSaveStream(mediaStorageInfos)
                .map(MediaStorageInfo::getId).collect(Collectors.toSet());
        mediaService.delete(ids, campFromDb.getId(), TypeEntity.CAMP);
        if (CollectionUtils.isNotEmpty(images)) {
            images.forEach(image -> mediaService.mediaInfoToMediaStorage(image, campFromDb.getId()));
        }
    }

    private void replaceMainImage(MediaStorageInfo mainImageInfo, CampEntity campFromDb) {
        if (Objects.nonNull(mainImageInfo) && Objects.nonNull(campFromDb.getMainImageId()) && !Objects.equals(mainImageInfo.getId(), campFromDb.getMainImage().getId())) {
            MediaStorageEntity mainImage = mediaService.mediaInfoToMediaStorage(mainImageInfo, campFromDb.getId());
            campFromDb.setMainImage(mainImage);
            campFromDb.setMainImageId(mainImage.getId());
        }
    }

    private void replaceImageCart(MediaStorageInfo cartImageInfo, CampEntity campFromDb) {
        if (Objects.nonNull(cartImageInfo) && Objects.nonNull(campFromDb.getMainImageId())  && !Objects.equals(cartImageInfo.getId(), campFromDb.getMainImage().getId())) {
            MediaStorageEntity imageCart = mediaService.mediaInfoToMediaStorage(cartImageInfo, campFromDb.getId());
            campFromDb.setCartImageId(imageCart.getId());
            campFromDb.setImageCart(imageCart);
        }
    }

    private void replaceCampCoaches(List<CoachDto> coaches, CampEntity campFromDb) {
        List<CampCoachEntity> campCoaches = campCoachRepository.findAllByIdCampId(campFromDb.getId());
        if (CollectionUtils.isNotEmpty(campCoaches)) {
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

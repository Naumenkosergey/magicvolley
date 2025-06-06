package ru.magicvolley.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import ru.magicvolley.exceptions.ValidationException;
import ru.magicvolley.repository.CampCoachRepository;
import ru.magicvolley.repository.CampPackageCardRepository;
import ru.magicvolley.repository.CampRepository;
import ru.magicvolley.repository.CampUserRepository;

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
    private final AuthService authService;
    private final DateStringService dateStringService;
    private final ProgramService programService;

    @Value("${media.prefix.url}")
    private String prefixUrlMedia;
    @Value("${filter.past.camp}")
    private boolean filterPastCamp;
    @Value("${nearest.camp.count}")
    private Integer nearestCampCount;
    private final CampUserRepository campUserRepository;

    @Transactional(readOnly = true)
    public List<CampDtoForList> getAll(CampType type) {
        List<CampEntity> campEntities = campRepository.findAllByCampType(type).stream()
                .sorted(Comparator.comparing(CampEntity::getDateStart))
                .toList();
        return getList(campEntities);
    }

    @Transactional(readOnly = true)
    public List<CampDtoForList> getAll() {
        List<CampEntity> campEntities = campRepository.findAll().stream()
                .sorted(Comparator.comparing(CampEntity::getDateStart)).toList();
        return getList(campEntities);
    }

    @Transactional(readOnly = true)
    public List<CampDtoForList> getAllNearest() {
        LocalDate now = LocalDate.now();
        List<CampEntity> campEntities = campRepository.findAll().stream()
                .filter(camp -> !now.isAfter(camp.getDateStart()))
                .sorted(Comparator.comparing(CampEntity::getDateStart))
                .limit(nearestCampCount)
                .toList();
        return getList(campEntities);
    }

    public List<CampDtoForList> getList(List<CampEntity> campEntities) {
        LocalDate now = LocalDate.now();
        return filterPastCamp
            ? campEntities.stream()
                .filter(camp -> now.isBefore(camp.getDateEnd()))
                .map(this::buildCampDtoForList)
                .toList()
            : campEntities.stream()
                .map(this::buildCampDtoForList)
                .toList();
    }

    public List<CampDtoForList> getCampList(List<CampEntity> campEntities, boolean isPast) {
        LocalDate now = LocalDate.now();
        return campEntities.stream()
                .filter(camp -> isPast ? now.isAfter(camp.getDateEnd()) : now.isBefore(camp.getDateEnd()))
                .sorted(Comparator.comparing(CampEntity::getDateStart))
                .map(this::buildCampDtoForList)
                .toList();
    }

    public CampDto buildCampDto(CampEntity campEntity) {
        boolean isPast = LocalDate.now().isAfter(campEntity.getDateEnd());
        List<MediaStorageInfo> galleryImages = isPast
                ? mediaService.getAllImagesForEntityIds(Set.of(campEntity.getId()), TypeEntity.PAST_GALLERY).get(campEntity.getId())
                : List.of();

        Boolean isAdminCurrentUser = Util.isAdminCurrentUser();
        return CampDto.builder()
                .id(campEntity.getId())
                .name(campEntity.getCampName())
                .info(campEntity.getInfo())
                .dateStart(campEntity.getDateStart())
                .dateEnd(campEntity.getDateEnd())
                .countFree(getCountFree(campEntity.getId()))
                .countAll(campEntity.getCountAll())
                .dateString(dateStringService.getDateString(campEntity.getDateStart(), campEntity.getDateEnd()))
                .coaches(campEntity.getCoaches().stream()
                        .filter(coach -> Objects.equals(Util.getOrDefaultIfNull(coach.isVisible(), Boolean.TRUE), Boolean.TRUE))
                        .map(x -> new CoachDto(x, prefixUrlMedia)).toList())
                .program(programService.getAllProgramForCamp(campEntity.getId()))
                .packages(isPast ? List.of() : campEntity.getPackages().stream().map(CampPackageCardDto::new).toList())
                .mainImage(new MediaStorageInfo(campEntity.getMainImage(), prefixUrlMedia))
                .imageCart(new MediaStorageInfo(campEntity.getImageCart(), prefixUrlMedia))
                .images(mediaService.getAllImagesForEntityIds(Set.of(campEntity.getId()), TypeEntity.CAMP).get(campEntity.getId()))
                .users(!isAdminCurrentUser
                        ? null
                        : campUserService.getUsersForCampId(campEntity.getId()))
                .gallery(galleryImages)
                .build();
    }

    private Integer getCountFree(UUID campId) {
        CampEntity campFromDb = campRepository.findById(campId)
                .orElseThrow(() -> new EntityNotFoundException(CampEntity.class, campId));
        Integer bookingCount = campUserService.getUsersForCampId(campId).stream()
                .filter(CampUserDto::getBookingConfirmed)
                .map(CampUserDto::getBookingCount)
                .reduce(Integer::sum)
                .orElse(0);
        return campFromDb.getCountAll() - bookingCount;
    }

    private CampDtoForList buildCampDtoForList(CampEntity campEntity) {
        return CampDtoForList.builder()
                .id(campEntity.getId())
                .name(campEntity.getCampName())
                .dateString(dateStringService.getDateString(campEntity.getDateStart(), campEntity.getDateEnd()))
                .imageCart(new MediaStorageInfo(campEntity.getImageCart(), prefixUrlMedia))
                .build();
    }

    @Transactional(readOnly = true)
    public CampDto getById(UUID id) {
        CampEntity campEntity = campRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найден кемп по id " + id));
        campUserService.recalculateIsViewUsers(campEntity.getId());
        return buildCampDto(campEntity);
    }

    @Transactional
    public UUID create(CampDto camp, CampType campType) {
        if (StringUtils.isBlank(camp.getName())) {
            throw new ValidationException("не указано название");
        }
        if (Objects.isNull(camp.getDateStart()) || Objects.isNull(camp.getDateEnd())) {
            throw new ValidationException("Не указана дата начала или окончания кемпа");
        }

        CampEntity campEntity = CampEntity.builder()
                .id(UUID.randomUUID())
                .campName(camp.getName())
                .info(camp.getInfo())
                .dateStart(camp.getDateStart())
                .dateEnd(camp.getDateEnd())
                .campType(campType)
                .countAll(camp.getCountAll())
                .build();
        campRepository.saveAndFlush(campEntity);

        MediaStorageEntity mainImage = mediaService.mediaInfoToMediaStorage(camp.getMainImage(), campEntity.getId(), TypeEntity.MAIN_IMAGE_CAMP);
        MediaStorageEntity imageCart = mediaService.mediaInfoToMediaStorage(camp.getImageCart(), campEntity.getId(), TypeEntity.CART_IMAGE_CAMP);
        setMainImage(mainImage, campEntity);
        setImageCart(imageCart, campEntity);
        loadImagesForCamp(camp.getImages(), campEntity);
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
                .totalPrice(Util.getOrDefaultIfNull(packageCard.getTotalPrice()))
                .bookingPrice(Util.getOrDefaultIfNull(packageCard.getBookingPrice()))
                .firstPrice(Util.getOrDefaultIfNull(packageCard.getFirstPrice()))
                .firstLimitation(packageCard.getFirstLimitation())
                .secondPrice(Util.getOrDefaultIfNull(packageCard.getSecondPrice()))
                .secondLimitation(packageCard.getSecondLimitation())
                .thirdPrice(Util.getOrDefaultIfNull(packageCard.getThirdPrice()))
                .thirdLimitation(packageCard.getThirdLimitation())
                .build();
    }

    private void createCampCoaches(Collection<CoachDto> coaches, CampEntity campEntity) {
        if (CollectionUtils.isNotEmpty(coaches)) {
            List<CampCoachEntity> campCoachEntities = coaches.stream()
                    .filter(coach -> Objects.equals(Util.getOrDefaultIfNull(coach.isVisible(), Boolean.TRUE), Boolean.TRUE))
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

        replaceImageCart(campRequest.getImageCart(), campFromDb);
        replaceMainImage(campRequest.getMainImage(), campFromDb);
        replaceCampCoaches(campRequest.getCoaches(), campFromDb);
        replaceCampPackages(campRequest.getPackages(), campFromDb);
        loadImagesForCamp(campRequest.getImages(), campFromDb);
        campRepository.save(campFromDb);
        return campFromDb.getId();
    }

    private void loadImagesForCamp(List<MediaStorageInfo> images, CampEntity campFromDb) {

        mediaService.deletedOldImagesUploadNewImages(images, campFromDb.getId(), TypeEntity.CAMP);
    }

    private void replaceMainImage(MediaStorageInfo mainImageInfo, CampEntity campFromDb) {
        if (Objects.nonNull(mainImageInfo) && !Objects.equals(mainImageInfo.getId(), campFromDb.getMainImageId())) {
            MediaStorageEntity mainImage = mediaService.mediaInfoToMediaStorage(mainImageInfo, campFromDb.getId(), TypeEntity.MAIN_IMAGE_CAMP);
            campFromDb.setMainImage(mainImage);
            campFromDb.setMainImageId(mainImage.getId());
        }
    }

    private void replaceImageCart(MediaStorageInfo cartImageInfo, CampEntity campFromDb) {
        if (Objects.nonNull(cartImageInfo) && !Objects.equals(cartImageInfo.getId(), campFromDb.getCartImageId())) {
            MediaStorageEntity imageCart = mediaService.mediaInfoToMediaStorage(cartImageInfo, campFromDb.getId(), TypeEntity.CART_IMAGE_CAMP);
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
        List<CampPackageCardEntity> campPackages = campPackageCardRepository.findAllByIdCampId(campId);
        campPackageCardRepository.deleteAll(campPackages);
        campCoachRepository.deleteAll(campCoaches);
        campRepository.delete(campFromDb);
        return true;
    }

    private static void setMainImage(MediaStorageEntity mediaStorage, CampEntity campEntity) {
        if (Objects.nonNull(mediaStorage)) {
            campEntity.setMainImage(mediaStorage);
            campEntity.setMainImageId(mediaStorage.getId());
        }
    }

    private static void setImageCart(MediaStorageEntity mediaStorage, CampEntity campEntity) {
        if (Objects.nonNull(mediaStorage)) {
            campEntity.setImageCart(mediaStorage);
            campEntity.setCartImageId(mediaStorage.getId());
        }
    }
}

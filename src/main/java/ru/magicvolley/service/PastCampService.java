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
import ru.magicvolley.enums.TypeEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampCoachRepository;
import ru.magicvolley.repository.CampRepository;
import ru.magicvolley.repository.CampUserRepository;
import ru.magicvolley.request.PastCampForUpdate;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Data
public class PastCampService {

    private final CampRepository campRepository;
    private final CampCoachRepository campCoachRepository;
    private final CampUserService campUserService;
    private final MediaService mediaService;
    private final AuthService authService;
    private final DateStringService dateStringService;

    @Value("${media.prefix.url}")
    private String prefixUrlMedia;
    private final CampUserRepository campUserRepository;


    @Transactional(readOnly = true)
    public List<CampDtoForList> getAll() {
        List<CampEntity> campEntities = campRepository.findAll().stream()
                .sorted(Comparator.comparing(CampEntity::getDateStart)).toList();
        return getList(campEntities);
    }

    public List<CampDtoForList> getList(List<CampEntity> campEntities) {
        LocalDate now = LocalDate.now();
        return campEntities.stream()
                .filter(camp -> camp.getDateEnd().isAfter(now))
                .map(this::buildCampDtoForList)
                .toList();
    }

    private PastCampDto buildPastCampDto(CampEntity campEntity, Map<UUID, List<MediaStorageInfo>> allImagesForCamIds, Map<UUID, List<MediaStorageInfo>>  allPastGalleryForCamId) {
        List<MediaStorageInfo> mediaStorageInfos = allImagesForCamIds.get(campEntity.getId());
        if (CollectionUtils.isNotEmpty(mediaStorageInfos)) {
            mediaStorageInfos.removeIf(x -> Objects.equals(x.getId(), campEntity.getMainImageId())
                    || Objects.equals(x.getId(), campEntity.getCartImageId()));
        }
        Boolean isAdminCurrentUser = Util.isAdminCurrentUser();
        return PastCampDto.builder()
                .id(campEntity.getId())
                .name(campEntity.getCampName())
                .info(campEntity.getInfo())
                .dateStart(campEntity.getDateStart())
                .dateEnd(campEntity.getDateEnd())
                .dateString(dateStringService.getDateString(campEntity.getDateStart(), campEntity.getDateEnd()))
                .coaches(campEntity.getCoaches().stream()
                        .filter(coach -> Objects.equals(Util.getOrDefaultIfNull(coach.isVisible(), Boolean.TRUE), Boolean.TRUE))
                        .map(x -> new CoachDto(x, prefixUrlMedia)).toList())
                .mainImage(new MediaStorageInfo(campEntity.getMainImage(), prefixUrlMedia))
                .imageCart(new MediaStorageInfo(campEntity.getImageCart(), prefixUrlMedia))
                .images(mediaStorageInfos)
                .users(!isAdminCurrentUser
                        ? null
                        : campUserService.getUsersForCampId(campEntity.getId()))
                .gallery(allPastGalleryForCamId.get(campEntity.getId()))
                .build();
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
    public PastCampDto getById(UUID id) {
        CampEntity campEntity = campRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("не найден кемп по id " + id));
        Map<UUID, List<MediaStorageInfo>> allImagesForCamId = mediaService.getAllImagesForEntityIds(Set.of(campEntity.getId()), TypeEntity.CAMP);
        Map<UUID, List<MediaStorageInfo>> allPastGalleryForCamId = mediaService.getAllImagesForEntityIds(Set.of(campEntity.getId()), TypeEntity.PAST_GALLERY);
        return buildPastCampDto(campEntity, allImagesForCamId, allPastGalleryForCamId);
    }

    @Transactional
    public UUID update(PastCampForUpdate campRequest, UUID campId) {
        CampEntity campFromDb = campRepository.findById(campId)
                .orElseThrow(() -> new EntityNotFoundException(CampEntity.class, campId));

        loadGalleryForCamp(campRequest.getGallery(), campFromDb);
        return campFromDb.getId();
    }

    private void loadGalleryForCamp(List<MediaStorageInfo> images, CampEntity campFromDb) {
        mediaService.deletedOldImagesUploadNewImages(images, campFromDb.getId(), TypeEntity.PAST_GALLERY);
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

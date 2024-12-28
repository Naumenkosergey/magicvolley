package ru.magicvolley.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.CampDtoForList;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.CampCoachEntity;
import ru.magicvolley.entity.CampEntity;
import ru.magicvolley.enums.TypeEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.CampCoachRepository;
import ru.magicvolley.repository.CampRepository;
import ru.magicvolley.repository.CampUserRepository;
import ru.magicvolley.request.PastCampForUpdate;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

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
                .filter(camp -> now.isAfter(camp.getDateEnd()))
                .map(this::buildCampDtoForList)
                .toList();
    }

    private CampDtoForList buildCampDtoForList(CampEntity campEntity) {
        return CampDtoForList.builder()
                .id(campEntity.getId())
                .name(campEntity.getCampName())
                .dateString(dateStringService.getDateString(campEntity.getDateStart(), campEntity.getDateEnd()))
                .imageCart(new MediaStorageInfo(campEntity.getImageCart(), prefixUrlMedia))
                .build();
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

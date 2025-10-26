package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.ManagerEntity;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.enums.TypeEntity;
import ru.magicvolley.repository.ManagerRepository;
import ru.magicvolley.request.HomeContactBlockRequest;
import ru.magicvolley.response.HomePageResponse;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    @Value("${media.prefix.url}")
    private String prefixUrlMedia;
    private final MediaService mediaService;

    @Transactional(readOnly = true)
    public List<HomePageResponse.ManagerInfo> getAll() {
        return managerRepository.findAll().stream()
                .map(this::mapManagerEntityToMAnagerInfo)
                .toList();
    }

    @Transactional
    public List<ManagerEntity> addManager(List<HomeContactBlockRequest.ManagerInfo> managerRequest) {

        List<ManagerEntity> managerEntities = managerRequest.stream()
                .map(managerInfo -> {
                    ManagerEntity managerEntity = ManagerEntity.builder()
                            .id(UUID.randomUUID())
                            .textUnderImage(managerInfo.getTextUnderImage())
                            .contacts(managerInfo.getContacts())
                            .email(managerInfo.getEmail())
                            .build();
                    MediaStorageEntity managerPhoto = mediaService.mediaInfoToMediaStorage(managerInfo.getImageAdmin(),
                            managerEntity.getId(), TypeEntity.MANAGER_PHOTO);
                    setManagerPhoto(managerPhoto, managerEntity);
                    return managerEntity;
                })
                .toList();

        managerRepository.saveAll(managerEntities);
        return managerEntities;
    }

    private HomePageResponse.ManagerInfo mapManagerEntityToMAnagerInfo(ManagerEntity manager) {
        return HomePageResponse.ManagerInfo.builder()
                .imageAdmin(new MediaStorageInfo(manager.getImageAdmin(), prefixUrlMedia))
                .contacts(manager.getContacts())
                .email(manager.getEmail())
                .textUnderImage(manager.getTextUnderImage())
                .build();
    }

    @Transactional
    public void deleteAllManagers() {
        List<ManagerEntity> all = managerRepository.findAll();
        managerRepository.deleteAll(all);
    }

    private static void setManagerPhoto(MediaStorageEntity mediaStorage, ManagerEntity managerEntity) {
        if (Objects.nonNull(mediaStorage)) {
            managerEntity.setImageAdmin(mediaStorage);
            managerEntity.setImageAdminId(mediaStorage.getId());
        }
    }
}

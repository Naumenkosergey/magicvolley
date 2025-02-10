package ru.magicvolley.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.magicvolley.Util;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.dto.MediaUploadDto;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.enums.TypeEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.MediaStorageRepository;
import ru.magicvolley.response.MediaResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Data
public class MediaService {

    private final MediaStorageRepository mediaRepository;

    @Value("${media.prefix.url}")
    private String prefixUrlMedia;

    @Transactional
    public MediaUploadDto uploadImage(MultipartFile file, TypeEntity typeEntity) {
        try {

            MediaStorageEntity entity = mapToMediaStorageEntity(file, typeEntity);
            mediaRepository.save(entity);
            return MediaUploadDto.builder()
                    .id(entity.getId())
                    .url(prefixUrlMedia + "/media/" + entity.getId().toString())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<MediaStorageEntity> getFile(UUID id) {
        return mediaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<MediaResponse> getAllFiles() {
        return mediaRepository.findAll().stream()
                .map(this::mapToFileResponse)
                .collect(Collectors.toList());
    }

    private MediaResponse mapToFileResponse(MediaStorageEntity mediaStorage) {
        String urlPath = prefixUrlMedia + "/media/" + mediaStorage.getId().toString();
        return MediaResponse.builder()
                .id(mediaStorage.getId())
                .name(mediaStorage.getFileName())
                .contentType(mediaStorage.getContentType())
                .size(mediaStorage.getSize())
                .url(urlPath)
                .build();

    }

    private MediaStorageEntity mapToMediaStorageEntity(MultipartFile file, TypeEntity typeEntity) throws IOException {
        return MediaStorageEntity.builder()
                .id(UUID.randomUUID())
                .fileName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())))
                .data(file.getBytes())
                .contentType(file.getContentType())
                .size(file.getSize())
                .typeEntity(typeEntity)
                .build();
    }

    public MediaStorageEntity mediaInfoToMediaStorage(MediaStorageInfo mediaInfo, UUID entityId, TypeEntity typeEntity) {
        if (Objects.nonNull(mediaInfo)) {
            try {
                if (Objects.isNull(mediaInfo.getId())) {
                    return createMediaStorage(mediaInfo, entityId, typeEntity);
                } else {
                    MediaStorageEntity mediaStorage = mediaRepository.findById(mediaInfo.getId())
                            .orElse(null);
                    if (Objects.isNull(mediaStorage)) {
                        return createMediaStorage(mediaInfo, entityId, typeEntity);
                    }
                    mediaStorage.setEntityId(entityId);
                    mediaStorage.setContentType(mediaInfo.getContentType());
                    mediaStorage.setTypeEntity(typeEntity);
                    mediaRepository.save(mediaStorage);
                    return mediaStorage;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    private MediaStorageEntity createMediaStorage(MediaStorageInfo mediaStorageInfo, UUID entityId, TypeEntity typeEntity) throws IOException {
        MediaStorageEntity mediaStorageEntity = MediaStorageEntity.builder()
                .id(UUID.randomUUID())
                .fileName(StringUtils.cleanPath(Objects.requireNonNull(mediaStorageInfo.getName())))
                .data(mediaStorageInfo.getData())
                .contentType(mediaStorageInfo.getContentType())
                .size(mediaStorageInfo.getSize())
                .typeEntity(typeEntity)
                .entityId(entityId)
                .build();
        mediaRepository.save(mediaStorageEntity);
        return mediaStorageEntity;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(MediaStorageEntity mediaStorage) {
        if (Objects.nonNull(mediaStorage) && Objects.nonNull(mediaStorage.getId())) {
            MediaStorageEntity mediaStorageFromDb = mediaRepository.findById(mediaStorage.getId())
                    .orElseThrow(() -> new EntityNotFoundException(MediaStorageEntity.class, mediaStorage.getId()));
            mediaRepository.delete(mediaStorageFromDb);
        }

    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(Collection<UUID> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            List<MediaStorageEntity> allById = mediaRepository.findAllById(ids);
            mediaRepository.deleteAll(allById);
        }
    }

    public void delete(UUID entityId, TypeEntity typeEntity) {

        List<MediaStorageEntity> mediaStorageFromDbs = mediaRepository
                .findAllByEntityIdAndTypeEntity(entityId, typeEntity);
        mediaRepository.deleteAll(mediaStorageFromDbs);
    }

    public void delete(Collection<UUID> ids, UUID entityId, TypeEntity typeEntity) {
        if (CollectionUtils.isNotEmpty(ids)) {
            List<MediaStorageEntity> mediaStorages = mediaRepository.findAllByIdInAndEntityIdAndTypeEntity(ids, entityId, typeEntity);
            if (CollectionUtils.isNotEmpty(mediaStorages)) {
                mediaRepository.deleteAll(mediaStorages);
            }
        }
    }

    public void update(MediaStorageEntity mediaStorage) {
        if (Objects.nonNull(mediaStorage) && Objects.nonNull(mediaStorage.getId())) {
            MediaStorageEntity mediaStorageFromDb = mediaRepository.findById(mediaStorage.getId())
                    .orElseThrow(() -> new EntityNotFoundException(MediaStorageEntity.class, mediaStorage.getId()));

            mediaRepository.delete(mediaStorageFromDb);
        }
    }

    public Map<UUID, List<MediaStorageInfo>> getAllImagesForEntityIds(Set<UUID> ids, TypeEntity typeEntity) {
        return mediaRepository.findAllByEntityIdInAndTypeEntity(ids, typeEntity).stream()
                .map(x -> new MediaStorageInfo(x, prefixUrlMedia))
                .collect(Collectors.groupingBy(MediaStorageInfo::getEntityId));
    }

    public List<MediaStorageInfo> getCollection(Collection<MediaStorageInfo> collection) {
        if (CollectionUtils.isNotEmpty(collection)) {
            return collection.stream().toList();
        }
        return Collections.emptyList();
    }

    public void deletedOldImagesUploadNewImages(List<MediaStorageInfo> imagesForRequest, UUID entityId, TypeEntity typeEntity) {
        Map<UUID, List<MediaStorageInfo>> allImagesForActivityIds = getAllImagesForEntityIds(Set.of(entityId), typeEntity);
        if (allImagesForActivityIds.isEmpty()) {
            Util.getSaveStream(imagesForRequest).forEach(image ->
                    mediaInfoToMediaStorage(image, entityId, typeEntity));
            return;
        }
        Set<UUID> imageIdsForRequest = Util.getSaveStream(imagesForRequest).map(MediaStorageInfo::getId).collect(Collectors.toSet());
        List<MediaStorageInfo> mediaStorageInfos = allImagesForActivityIds.get(entityId);
        if (CollectionUtils.isNotEmpty(mediaStorageInfos)) {
            mediaStorageInfos.removeIf(x -> imageIdsForRequest.contains(x.getId()));
        }
        Set<UUID> ids = Util.getSaveStream(mediaStorageInfos)
                .map(MediaStorageInfo::getId).collect(Collectors.toSet());
        delete(ids, entityId, typeEntity);
        if (CollectionUtils.isNotEmpty(imagesForRequest)) {
            imagesForRequest.forEach(image ->
                    mediaInfoToMediaStorage(image, entityId, typeEntity));
        }
    }

    public List<MediaStorageInfo> getAllImages(TypeEntity typeEntity) {
        List<MediaStorageInfo> medias = Util.getSaveStream(mediaRepository.findAllByTypeEntity(typeEntity))
                .map(x -> new MediaStorageInfo(x, prefixUrlMedia))
                .limit(100)
                .collect(Collectors.toList());
        Collections.shuffle(medias);
        return medias;
    }


    public List<MediaStorageInfo> getAllImages(UUID entityId, TypeEntity typeEntity) {

        return mediaRepository
                .findAllByEntityIdAndTypeEntity(entityId, typeEntity).stream()
                .map(x -> new MediaStorageInfo(x, prefixUrlMedia))
                .collect(Collectors.toList());
    }
}



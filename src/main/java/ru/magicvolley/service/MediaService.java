package ru.magicvolley.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
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
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
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

    public MediaStorageEntity mediaInfoToMediaStorage(MediaStorageInfo mediaInfo, UUID entityId) {
        if (Objects.nonNull(mediaInfo)) {
            try {
                if (Objects.isNull(mediaInfo.getId())) {
                    return createMediaStorage(mediaInfo, entityId);
                } else {
                    return mediaRepository.findById(mediaInfo.getId())
                            .orElseThrow(() -> new EntityNotFoundException(MediaStorageEntity.class, mediaInfo.getId()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    private MediaStorageEntity createMediaStorage(MediaStorageInfo mediaStorageInfo, UUID entityId) throws IOException {
        MediaStorageEntity mediaStorageEntity = MediaStorageEntity.builder()
                .id(UUID.randomUUID())
                .fileName(StringUtils.cleanPath(Objects.requireNonNull(mediaStorageInfo.getName())))
                .data(mediaStorageInfo.getData())
                .contentType(mediaStorageInfo.getContentType())
                .size(mediaStorageInfo.getSize())
                .typeEntity(mediaStorageInfo.getTypeEntity())
                .entityId(entityId)
                .build();
        mediaRepository.save(mediaStorageEntity);
        return mediaStorageEntity;
    }

    public void delete(MediaStorageEntity mediaStorage) {
        if (Objects.nonNull(mediaStorage) && Objects.nonNull(mediaStorage.getId())) {
            MediaStorageEntity mediaStorageFromDb = mediaRepository.findById(mediaStorage.getId())
                    .orElseThrow(() -> new EntityNotFoundException(MediaStorageEntity.class, mediaStorage.getId()));
            mediaRepository.delete(mediaStorageFromDb);
        }

    }

    public Map<UUID, List<MediaStorageInfo>> getAllImagesForCamIds(Set<UUID> ids) {
        return mediaRepository.findAllByEntityIdIn(ids).stream()
                .map( x-> new MediaStorageInfo(x, prefixUrlMedia))
                .collect(Collectors.groupingBy(MediaStorageInfo::getEntityId));
    }
}



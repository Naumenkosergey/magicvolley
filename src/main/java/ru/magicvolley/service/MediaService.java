package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.magicvolley.dto.MediaStorageInfo;
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
public class MediaService {

    private final MediaStorageRepository mediaRepository;

    @Transactional
    public ResponseEntity<String> uploadImage(MultipartFile file, TypeEntity typeEntity) {
        try {
            mediaRepository.save(mapToMediaStorageEntity(file, typeEntity));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
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
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/media/")
                .path(mediaStorage.getId().toString())
                .toUriString();
        return MediaResponse.builder()
                .id(mediaStorage.getId())
                .name(mediaStorage.getFileName())
                .contentType(mediaStorage.getContentType())
                .size(mediaStorage.getSize())
                .url(downloadURL)
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

    public MediaStorageEntity mediaInfoToMediaStorage(MediaStorageInfo mediaInfo) {
        if (Objects.nonNull(mediaInfo)) {
            try {
                if (Objects.isNull(mediaInfo.getId())) {
                    return createMediaStorage(mediaInfo);
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

    public List<MediaStorageEntity> mediaInfoToMediaStorage(List<MediaStorageInfo> mediaInfos)  {

        List<MediaStorageEntity> mediaStorageEntities = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(mediaInfos)) {
            mediaInfos.forEach(mediaStorageInfo -> {
                if (Objects.isNull(mediaStorageInfo.getId())) {
                    try {
                        MediaStorageEntity mediaStorage = createMediaStorage(mediaStorageInfo);
                        mediaStorageEntities.add(mediaStorage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    mediaRepository.findById(mediaStorageInfo.getId())
                            .orElseThrow(() -> new EntityNotFoundException(MediaStorageEntity.class, mediaStorageInfo.getId()));
                }
            });
        }
        return mediaStorageEntities;
    }


    private MediaStorageEntity createMediaStorage(MediaStorageInfo mediaStorageInfo) throws IOException {
        MediaStorageEntity mediaStorageEntity = MediaStorageEntity.builder()
                .id(UUID.randomUUID())
                .fileName(StringUtils.cleanPath(Objects.requireNonNull(mediaStorageInfo.getName())))
                .data(mediaStorageInfo.getData())
                .contentType(mediaStorageInfo.getContentType())
                .size(mediaStorageInfo.getSize())
                .typeEntity(mediaStorageInfo.getTypeEntity())
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
}



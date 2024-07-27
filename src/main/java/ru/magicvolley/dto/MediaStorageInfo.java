package ru.magicvolley.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.enums.TypeEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaStorageInfo {

    private UUID id;
    private String fileName;
    private String contentType;
    private Long size;
    @Enumerated(EnumType.STRING)
    private TypeEntity typeEntity;
    private byte[] data;
    private LocalDateTime updateAt;

    public MediaStorageInfo(MediaStorageEntity imageStorage) {
        this.id = imageStorage.getId();
        this.fileName = imageStorage.getFileName();
        this.contentType = imageStorage.getContentType();
        this.size = imageStorage.getSize();
        this.typeEntity = imageStorage.getTypeEntity();
        this.data = imageStorage.getData();
    }
}

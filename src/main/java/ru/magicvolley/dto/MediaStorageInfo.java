package ru.magicvolley.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.enums.TypeEntity;

import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaStorageInfo {

    private UUID id;
    private UUID entityId;
    private String name;
    private String contentType;
    private Long size;
    @Enumerated(EnumType.STRING)
    private TypeEntity typeEntity;
    private byte[] data;
    private String url;


    public MediaStorageInfo(MediaStorageEntity imageStorage, String prefixUrlMedia) {

        if (Objects.nonNull(imageStorage) && Objects.nonNull(imageStorage.getId())) {
            String urlPath = prefixUrlMedia + "/media/" + imageStorage.getId().toString();

            this.id = imageStorage.getId();
            this.entityId = imageStorage.getEntityId();
            this.name = imageStorage.getFileName();
            this.contentType = imageStorage.getContentType();
            this.size = imageStorage.getSize();
            this.typeEntity = imageStorage.getTypeEntity();
//        this.data = imageStorage.getData();
            this.url = urlPath;
        }
    }

}

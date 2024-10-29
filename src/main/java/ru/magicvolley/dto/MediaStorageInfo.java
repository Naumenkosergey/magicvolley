package ru.magicvolley.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.enums.TypeEntity;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaStorageInfo {

    private UUID id;
    private String name;
    private String contentType;
    private Long size;
    @Enumerated(EnumType.STRING)
    private TypeEntity typeEntity;
    private byte[] data;
    private String url;

    public MediaStorageInfo(MediaStorageEntity imageStorage) {

        String urlPath = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/media/")
                .path(imageStorage.getId().toString())
                .toUriString();

        this.id = imageStorage.getId();
        this.name = imageStorage.getFileName();
        this.contentType = imageStorage.getContentType();
        this.size = imageStorage.getSize();
        this.typeEntity = imageStorage.getTypeEntity();
        this.data = imageStorage.getData();
        this.url = urlPath;
    }
}

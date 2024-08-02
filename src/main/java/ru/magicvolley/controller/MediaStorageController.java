package ru.magicvolley.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.enums.TypeEntity;
import ru.magicvolley.response.MediaResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.MediaService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/media", produces = MediaType.APPLICATION_JSON_VALUE)
public class MediaStorageController {

    private final MediaService mediaService;

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("typeEntity") TypeEntity typeEntity) {

        return mediaService.uploadImage(file, typeEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID id) {
        Optional<MediaStorageEntity> fileEntityOptional = mediaService.getFile(id);

        if (fileEntityOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }

        MediaStorageEntity mediaStorage = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; " +
                        "filename=\"" + mediaStorage.getFileName() + "\"")
                .contentType(MediaType.valueOf(mediaStorage.getContentType()))
                .body(mediaStorage.getData());
    }

    @GetMapping("/all")
    public ApiResponse<List<MediaResponse>> list() {
        List<MediaResponse> allFiles = mediaService.getAllFiles();
        return new ApiResponse<>(allFiles);
    }
}

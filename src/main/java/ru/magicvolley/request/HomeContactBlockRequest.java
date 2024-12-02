package ru.magicvolley.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.dto.MediaStorageInfo;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeContactBlockRequest {

    private UUID id;
    private MediaStorageInfo imageAdmin;
    private String textUnderImage;
    private String linkVk;
    private String linkTg;
    private String linkInstagram;
    private String email;
    private String contacts;
}

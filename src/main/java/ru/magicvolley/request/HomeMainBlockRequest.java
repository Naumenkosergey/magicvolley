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
public class HomeMainBlockRequest {

    private UUID id;
    private String title;
    private String subtitle;
    private MediaStorageInfo mainImage;
}

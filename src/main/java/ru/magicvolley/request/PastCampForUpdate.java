package ru.magicvolley.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.dto.MediaStorageInfo;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PastCampForUpdate {
    private List<MediaStorageInfo> gallery;
}

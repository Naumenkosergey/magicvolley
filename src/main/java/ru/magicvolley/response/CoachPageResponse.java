package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.dto.CoachDto;
import ru.magicvolley.dto.MediaStorageInfo;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachPageResponse {

    private List<CoachDto> coaches;
    private List<MediaStorageInfo> mediaCoaches;
}

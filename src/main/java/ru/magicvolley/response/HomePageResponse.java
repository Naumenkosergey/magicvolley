package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.dto.CampDto;
import ru.magicvolley.dto.CoachDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomePageResponse {

    private List<CampDto> camps;
    private List<CoachDto> coaches;
    private List<MediaResponse> medias;
    private List<QuestionResponse> questions;

}

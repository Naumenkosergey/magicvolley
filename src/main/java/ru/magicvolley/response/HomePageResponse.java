package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.dto.CampDtoForList;
import ru.magicvolley.dto.CoachDto;
import ru.magicvolley.dto.MediaStorageInfo;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomePageResponse {

    private UUID id;
    private List<CampDtoForList> camps;
    private List<CoachDto> coaches;
    private List<QuestionResponse> questions;

    MainBlockResponse mainBlock;
    ContactBlockResponse contactBlock;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MainBlockResponse {
        private String title;
        private String subtitle;
        private MediaStorageInfo mainImage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ContactBlockResponse {

        private List<ManagerInfo> managers;
        private String linkVk;
        private String linkTg;
        private String linkInstagram;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ManagerInfo {
        private MediaStorageInfo imageAdmin;
        private String textUnderImage;
        private String email;
        private String contacts;
    }
}

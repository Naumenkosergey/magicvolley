package ru.magicvolley.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.dto.MediaStorageInfo;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutUsRequest {

    private String title;
    private String subTitleFirst;
    private String subTitleSecond;
    private Integer numberOfWorkouts;
    private Integer numberOfCamps;
    private Integer numberOfStudents;
    private Master master;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Activity {
        private UUID id;
        private String name;
        private List<MediaStorageInfo> images;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Video {
        private UUID id;
        private String name;
        private String url;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Master {
        private UUID id;
        private String name;
        private List<String> infos;
        private MediaStorageInfo image;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Review {
        private UUID id;
        private String name;
        private String date;
        private String comment;
        private MediaStorageInfo image;
    }
}

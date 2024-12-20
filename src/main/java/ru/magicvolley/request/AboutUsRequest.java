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
    private List<Activity> Activities;
    private Video video;
    private Master master;
    private List<Review> reviews;

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
        private String name;
        private List<String> infos;
        private MediaStorageInfo image;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Review {
        private String name;
        private String date;
        private String comment;
        private MediaStorageInfo image;
    }
}

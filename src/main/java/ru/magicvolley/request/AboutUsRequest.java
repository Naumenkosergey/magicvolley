package ru.magicvolley.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.dto.MediaStorageInfo;

import java.util.List;

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
    public static class Master {
        private String name;
        private List<String> infos;
        private MediaStorageInfo image;
    }
}

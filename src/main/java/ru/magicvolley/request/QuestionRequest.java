package ru.magicvolley.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuestionRequest {

    List<Question> questions;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Question {

        private UUID id;
        private String question;
        private String answer;
    }
}

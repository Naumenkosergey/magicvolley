package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.QuestionEntity;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuestionResponse {

    private UUID id;
    private String question;
    private String answer;

    public QuestionResponse(QuestionEntity questionEntity) {
        this.id = questionEntity.getId();
        this.question = questionEntity.getQuestion();
        this.answer = questionEntity.getAnswer();
    }
}

package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.QuestionEntity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuestionResponse {

    private String question;
    private String answer;

    public QuestionResponse(QuestionEntity questionEntity) {
        this.question = questionEntity.getQuestion();
        this.answer = questionEntity.getAnswer();
    }
}

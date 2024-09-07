package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.entity.QuestionEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.QuestionRepository;
import ru.magicvolley.request.QuestionRequest;
import ru.magicvolley.response.QuestionResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<QuestionResponse> getAll() {

        return questionRepository.findAll().stream()
                .map(QuestionResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UUID create(QuestionRequest questionRequest) {

        QuestionEntity questionEntity = QuestionEntity.builder()
                .id(UUID.randomUUID())
                .question(questionRequest.getQuestion())
                .answer(questionRequest.getAnswer())
                .build();
        questionRepository.save(questionEntity);
        return questionEntity.getId();
    }

    @Transactional
    public UUID update(QuestionRequest questionRequest) {

        QuestionEntity questionEntity = questionRepository.findById(questionRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException(QuestionEntity.class, questionRequest.getId()));
        questionEntity.setQuestion(questionRequest.getQuestion());
        questionEntity.setAnswer(questionRequest.getAnswer());
        questionRepository.save(questionEntity);
        return questionEntity.getId();
    }

    @Transactional
    public UUID delete(UUID questionId) {
        QuestionEntity questionEntity = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException(QuestionEntity.class, questionId));
        questionRepository.delete(questionEntity);
        return questionId;
    }

    @Transactional(readOnly = true)
    public QuestionResponse getById(UUID questionId) {
        QuestionEntity questionEntity = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException(QuestionEntity.class, questionId));
        return new QuestionResponse(questionEntity);
    }
}

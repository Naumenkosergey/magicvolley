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
import java.util.Objects;
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
    public Boolean create(List<QuestionRequest> questionRequest) {
        questionRepository.deleteAll();

        List<QuestionEntity> questions = questionRequest.stream()
                .map(questionReq -> QuestionEntity.builder()
                        .id(UUID.randomUUID())
                        .question(questionReq.getQuestion())
                        .answer(questionReq.getAnswer())
                        .build()
                ).toList();
        questionRepository.saveAll(questions);
        return true;
    }

    @Transactional
    public Boolean update(List<QuestionRequest> questionRequest) {

        List<QuestionEntity> questions = questionRequest.stream()
                .map(questionReq -> {
                            UUID id = Objects.nonNull(questionReq.getId()) ? questionReq.getId() : UUID.randomUUID();
                            return QuestionEntity.builder()
                                    .id(id)
                                    .question(questionReq.getQuestion())
                                    .answer(questionReq.getAnswer())
                                    .build();
                        }
                ).toList();
        questionRepository.saveAll(questions);

        return true;
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

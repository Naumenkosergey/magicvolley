package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.repository.QuestionRepository;
import ru.magicvolley.response.QuestionResponse;

import java.util.List;
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
}

package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.magicvolley.entity.QuestionEntity;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
}
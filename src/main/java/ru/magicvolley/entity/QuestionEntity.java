package ru.magicvolley.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "questions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEntity {

    @Id
    private UUID id;
    private String question;
    private String answer;

    @Version
    private Long version;
}

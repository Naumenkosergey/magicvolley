package com.example.demomv.service;

import com.example.demomv.dto.CoachDto;
import com.example.demomv.entity.CoachEntity;
import com.example.demomv.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;

    @Transactional
    public List<CoachEntity> getAll() {
        return coachRepository.findAll();
    }

    public CoachEntity getById(UUID id) {
        return coachRepository.findById(id).orElse(null);
    }

    public CoachEntity create(CoachDto coach) {
        return coachRepository.save(CoachEntity.builder()
                .id(UUID.randomUUID())
                .name(coach.getName())
                .surename(coach.getSurename())
                .info(coach.getInfo())
                .build());
    }

    public CoachEntity update(CoachEntity coach) {
        return coachRepository.save(coach);
    }

    public void delete(UUID id) {
        coachRepository.deleteById(id);
    }

}

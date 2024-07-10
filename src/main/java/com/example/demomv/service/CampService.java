package com.example.demomv.service;

import com.example.demomv.Util;
import com.example.demomv.entity.CampCoachEntity;
import com.example.demomv.repository.CampCoachRepository;
import com.example.demomv.repository.CampRepository;
import com.example.demomv.dto.CampDto;
import com.example.demomv.entity.CampEntity;
import com.example.demomv.repository.CoachRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.demomv.Util.*;

@Service
@RequiredArgsConstructor
public class CampService {

    private final CampRepository campRepository;
    private final CoachRepository coachRepository;
    private final CampCoachRepository campCoachRepository;

    @Transactional
    public List<CampDto> getAll() {
        List<CampEntity> campEntities = campRepository.findAll();
        return campEntities.stream().map(campEntity -> CampDto.builder()
                        .id(campEntity.getId())
                        .price(campEntity.getPrice())
                        .name(campEntity.getName())
                        .info(campEntity.getInfo())
                        .dateStart(campEntity.getDateStart().toString())
                        .dateEnd(campEntity.getDateEnd().toString())
                        .countFree(campEntity.getCountFree())
                        .countAll(campEntity.getCountAll())
                        .coaches(campEntity.getCampCoaches().stream().map(x -> x.getId().getCoachId()).toList())
                        .build()).
                toList();
    }

    public CampDto getById(UUID id) {
        CampEntity campEntity = campRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("не найден кемп по id " + id));
        return CampDto.builder()
                .id(id)
                .price(campEntity.getPrice())
                .name(campEntity.getName())
                .info(campEntity.getInfo())
                .dateStart(campEntity.getDateStart().toString())
                .dateEnd(campEntity.getDateEnd().toString())
                .countFree(campEntity.getCountFree())
                .countAll(campEntity.getCountAll())
                .coaches(campEntity.getCampCoaches().stream().map(x -> x.getId().getCoachId()).toList())
                .build();

    }

    public CampEntity create(CampDto camp) {
        CampEntity campEntity = CampEntity.builder()
                .id(UUID.randomUUID())
                .name(camp.getName())
                .info(camp.getInfo())
                .dateStart(Objects.nonNull(camp.getDateStart()) ?
                        LocalDate.parse(camp.getDateStart())
                        : null)
                .dateEnd(Objects.nonNull(camp.getDateEnd())
                        ? LocalDate.parse(camp.getDateEnd())
                        : null)
                .price(camp.getPrice())
                .countAll(camp.getCountAll())
                .countFree(camp.getCountFree())
                .build();
        campRepository.saveAndFlush(campEntity);
        List<CampCoachEntity> campCoachEntities = camp.getCoaches().stream()
                .map(coachId -> CampCoachEntity.builder()
                        .id(CampCoachEntity.Id.builder()
                                .campId(campEntity.getId())
                                .coachId(coachId)
                                .build())
                        .camp(campEntity)
                        .coach(coachRepository.findById(coachId)
                                .orElseThrow(() -> new EntityNotFoundException("не найден тренер по id " + coachId)))
                        .build())
                .toList();

        campCoachRepository.saveAll(campCoachEntities);
        return campEntity;
    }

    public CampEntity update(CampEntity camp) {
        return campRepository.save(camp);
    }

    public void delete(UUID id) {
        campRepository.deleteById(id);
    }
}

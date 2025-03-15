package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.ProgramDto;
import ru.magicvolley.dto.ProgramForCamp;
import ru.magicvolley.entity.ProgramEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.ProgramRepository;
import ru.magicvolley.request.ProgramRequest;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;

    @Transactional
    public UUID createProgram(ProgramRequest programRequest) {

        ProgramEntity programEntity = ProgramEntity.builder()
                .id(UUID.randomUUID())
                .info(programRequest.info())
                .dayOfWeek(programRequest.dayOfWeek())
                .order(programRequest.order())
                .campId(programRequest.campId())
                .build();
        programRepository.save(programEntity);
        return programEntity.getId();
    }

    @Transactional
    public UUID updateProgram(ProgramRequest programRequest) {
        ProgramEntity programFromDb = programRepository.findById(programRequest.id())
                .orElseThrow(() -> new EntityNotFoundException("Не найдена программа по id " + programRequest.id()));

        programFromDb.setInfo(programRequest.info());
        programFromDb.setDayOfWeek(programRequest.dayOfWeek());
        programFromDb.setOrder(programRequest.order());
        programRepository.save(programFromDb);
        return programFromDb.getId();
    }

    @Transactional
    public Boolean delete(UUID id) {
        ProgramEntity programFromDb = programRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена программа по id " + id));

        programRepository.delete(programFromDb);
        return true;
    }

    @Transactional(readOnly = true)
    public ProgramForCamp getAllProgramForCamp(UUID campId) {
        List<ProgramEntity> programs = programRepository.findAllByCampId(campId);

        List<ProgramDto> programsDto = programs.stream()
                .map(program -> ProgramDto.builder()
                        .id(program.getId())
                        .info(program.getInfo())
                        .dayOfWeek(program.getDayOfWeek())
                        .order(program.getOrder())
                        .campId(program.getCampId())
                        .build())
                .sorted(Comparator.comparing(ProgramDto::getOrder))
                .toList();
        return new ProgramForCamp(programsDto);
    }
}

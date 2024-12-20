package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.ScheduleDto;
import ru.magicvolley.entity.ScheduleEntity;
import ru.magicvolley.entity.ScheduleGroupEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.ScheduleGroupRepository;
import ru.magicvolley.repository.ScheduleRepository;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleGroupRepository scheduleGroupRepository;

    @Transactional(readOnly = true)
    public List<ScheduleDto> getSchedule() {

        List<ScheduleGroupEntity> scheduleGroupsFromDb = scheduleGroupRepository.findAll();

        var scheduleGroupIdToSchedule = scheduleRepository.findAll()
                .stream().collect(Collectors.groupingBy(ScheduleEntity::getScheduleGroupId));

        Map<UUID, ScheduleGroupEntity> scheduleGroupToSchedule = scheduleGroupsFromDb.stream()
                .collect(Collectors.toMap(ScheduleGroupEntity::getId, Function.identity()));

        return scheduleGroupsFromDb.stream()
                .map(scheduleGroup -> new ScheduleDto(scheduleGroup, scheduleGroupIdToSchedule.get(scheduleGroup.getId())))
                .sorted(Comparator.comparing(o -> scheduleGroupToSchedule.get(o.getId()).getOrderNumber()))
                .collect(Collectors.toList());
    }

    @Transactional
    public UUID create(ScheduleDto scheduleDto) {
        int lastNumber = scheduleRepository.findAll().size();
        ScheduleGroupEntity scheduleGroupEntity = ScheduleGroupEntity.builder()
                .id(UUID.randomUUID())
                .groupName(scheduleDto.getName())
                .orderNumber(lastNumber + 1)
                .build();

        List<ScheduleEntity> scheduleEntities = getNewSchedulesFromRequest(scheduleDto, scheduleGroupEntity.getId());

        scheduleGroupRepository.save(scheduleGroupEntity);
        if (CollectionUtils.isNotEmpty(scheduleEntities)) {
            scheduleRepository.saveAll(scheduleEntities);
        }

        return scheduleGroupEntity.getId();
    }

    private static List<ScheduleEntity> getNewSchedulesFromRequest(ScheduleDto scheduleDto, UUID scheduleGroupId) {
        return scheduleDto.getDays().stream()
                .filter(day -> StringUtils.isNoneBlank(day.getTime()) && StringUtils.isNoneBlank(day.getAddress()))
                .map(day -> ScheduleEntity.builder()
                        .id(UUID.randomUUID())
                        .day(day.getId())
                        .scheduleGroupId(scheduleGroupId)
                        .trainingTime(LocalTime.of(Integer.parseInt(day.getTime().split(":")[0]),
                                Integer.parseInt(day.getTime().split(":")[1])))
                        .address(day.getAddress())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ScheduleDto getById(UUID id) {
        ScheduleGroupEntity scheduleGroupFromDb = scheduleGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ScheduleEntity.class, id));

        var schedules = scheduleRepository.findAllByScheduleGroupId(scheduleGroupFromDb.getId());
        return new ScheduleDto(scheduleGroupFromDb, schedules);
    }

    @Transactional
    public UUID update(ScheduleDto request) {
        ScheduleGroupEntity scheduleGroupFromDb = scheduleGroupRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(ScheduleEntity.class, request.getId()));

        scheduleGroupFromDb.setGroupName(request.getName());
        var schedules = scheduleRepository.findAllByScheduleGroupId(scheduleGroupFromDb.getId());
        scheduleRepository.deleteAll(schedules);

        List<ScheduleEntity> newSchedules = getNewSchedulesFromRequest(request, scheduleGroupFromDb.getId());
        if (CollectionUtils.isNotEmpty(newSchedules)) {
            scheduleRepository.saveAll(newSchedules);
        }
        return scheduleGroupFromDb.getId();
    }

    @Transactional
    public Boolean delete(UUID id) {
        ScheduleGroupEntity scheduleGroupFromDb = scheduleGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ScheduleEntity.class, id));

        List<ScheduleEntity> schedulesFromDb = scheduleRepository.findAllByScheduleGroupId(scheduleGroupFromDb.getId());
        scheduleRepository.deleteAll(schedulesFromDb);
        scheduleGroupRepository.delete(scheduleGroupFromDb);
        return true;
    }
}

package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.entity.ScheduleEntity;
import ru.magicvolley.entity.ScheduleGroupEntity;
import ru.magicvolley.repository.ScheduleGroupRepository;
import ru.magicvolley.repository.ScheduleRepository;
import ru.magicvolley.response.ScheduleResponse;

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
    public List<ScheduleResponse> getSchedule() {


        var scheduleGroupIdToSchedule = scheduleRepository.findAll()
                .stream().collect(Collectors.groupingBy(ScheduleEntity::getScheduleGroupId));

        Map<UUID, ScheduleGroupEntity> scheduleGroupToSchedule = scheduleGroupRepository.findAllById(scheduleGroupIdToSchedule.keySet()).stream()
                .collect(Collectors.toMap(ScheduleGroupEntity::getId, Function.identity()));

        return scheduleGroupIdToSchedule.entrySet().stream()
                .map(entry -> new ScheduleResponse(entry, scheduleGroupToSchedule))
                .sorted(Comparator.comparing(o -> scheduleGroupToSchedule.get(o.getId()).getOrderNumber()))
                .collect(Collectors.toList());
    }
}

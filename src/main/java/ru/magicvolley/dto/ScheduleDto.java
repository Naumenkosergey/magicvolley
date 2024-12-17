package ru.magicvolley.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.ScheduleEntity;
import ru.magicvolley.entity.ScheduleGroupEntity;
import ru.magicvolley.enums.DayOfWeek;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ScheduleDto {

    private UUID id;
    private String name;
    private List<Day> days;

    public ScheduleDto(Map.Entry<UUID, List<ScheduleEntity>> entry,
                       Map<UUID, ScheduleGroupEntity> scheduleGroupToSchedule) {

        this.id = entry.getKey();
        this.name = scheduleGroupToSchedule.get(entry.getKey()).getGroupName();
        this.days = entry.getValue().stream()
                .map(Day::new)
                .collect(Collectors.toList());
    }

    public ScheduleDto(ScheduleGroupEntity scheduleGroupFromDb, List<ScheduleEntity> schedules) {
        this.id = scheduleGroupFromDb.getId();
        this.name = scheduleGroupFromDb.getGroupName();
        this.days = schedules.stream()
                .map(Day::new)
                .collect(Collectors.toList());
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Day {
        @Enumerated(value = EnumType.STRING)
        private DayOfWeek id;
        private String time;
        private String address;

        public Day(ScheduleEntity shedule) {
            this.id = shedule.getDay();
            this.address = shedule.getAddress();
            this.time = getTimeFormatString(shedule.getTrainingTime());
        }

        private String getTimeFormatString(LocalTime startTime) {

            return String.format("%02d:%02d", startTime.getHour(), startTime.getMinute());
//            String startTimeString = String.format("%02d:%02d", startTime.getHour(), startTime.getMinute());
//            String endTimeString = String.format("%02d:%02d", endTime.getHour(), endTime.getMinute());
//            return startTimeString + "-" + endTimeString;
        }
    }
}
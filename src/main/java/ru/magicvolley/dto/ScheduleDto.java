package ru.magicvolley.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.Util;
import ru.magicvolley.entity.ScheduleEntity;
import ru.magicvolley.entity.ScheduleGroupEntity;
import ru.magicvolley.enums.DayOfWeek;

import java.time.LocalTime;
import java.util.List;
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
    private String link;

    public ScheduleDto(ScheduleGroupEntity scheduleGroupFromDb, List<ScheduleEntity> schedules) {
        this.id = scheduleGroupFromDb.getId();
        this.name = scheduleGroupFromDb.getGroupName();
        this.days = Util.getSaveStream(schedules)
                .map(Day::new)
                .collect(Collectors.toList());
        this.link = scheduleGroupFromDb.getLink();
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
        }
    }
}
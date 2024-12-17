package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.enums.DayOfWeek;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "schedule")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ScheduleEntity {
    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private DayOfWeek day;
    private LocalTime trainingTime;
    private String address;
    private Long version;

    private UUID scheduleGroupId;

    @ManyToOne
    @JoinColumn(name = "scheduleGroupId", insertable = false, updatable = false)
    private ScheduleGroupEntity scheduleGroup;
}
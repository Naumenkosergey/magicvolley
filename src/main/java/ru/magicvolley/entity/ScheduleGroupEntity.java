package ru.magicvolley.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "schedule_groups")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheduleGroupEntity {
    @Id
    private UUID id;
    private String groupName;
    private Long version;
    private Integer orderNumber;


//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "scheduleGroupId", insertable = false, updatable = false)
//    private List<ScheduleEntity> schedule;
}

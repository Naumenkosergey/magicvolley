package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.ScheduleGroupEntity;

import java.util.UUID;

@Repository
public interface ScheduleGroupRepository extends JpaRepository<ScheduleGroupEntity, UUID> {
}
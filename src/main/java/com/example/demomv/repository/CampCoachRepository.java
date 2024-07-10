package com.example.demomv.repository;

import com.example.demomv.entity.CampCoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampCoachRepository extends JpaRepository<CampCoachEntity, CampCoachEntity.Id> {
    List<CampCoachEntity> findCampCoachEntitiesByIdCampId(UUID id);
}

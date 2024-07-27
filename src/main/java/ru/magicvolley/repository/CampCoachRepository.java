package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.CampCoachEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampCoachRepository extends JpaRepository<CampCoachEntity, CampCoachEntity.Id> {

    List<CampCoachEntity> findAllByIdCampId(UUID campId);

    List<CampCoachEntity> findAllByIdCoachId(UUID coachId);
}

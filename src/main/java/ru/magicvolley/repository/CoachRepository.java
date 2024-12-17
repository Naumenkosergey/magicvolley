package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.CoachEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, UUID> {

    List<CoachEntity> findAllByCoachTypeLike(String type);
}

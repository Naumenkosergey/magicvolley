package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.CampUserEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampUserRepository extends JpaRepository<CampUserEntity, CampUserEntity.Id> {

    public List<CampUserEntity> findByIdCampId(UUID campId);
}
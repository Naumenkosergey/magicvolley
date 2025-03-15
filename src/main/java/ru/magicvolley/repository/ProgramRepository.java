package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.ProgramEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProgramRepository extends JpaRepository<ProgramEntity, UUID> {

    List<ProgramEntity> findAllByCampId(UUID campId);
}
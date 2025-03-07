package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.MasterEntity;

import java.util.UUID;
@Repository
public interface MasterRepository extends JpaRepository<MasterEntity, UUID> {
}
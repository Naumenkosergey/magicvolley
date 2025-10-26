package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.ManagerEntity;

import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, UUID> {
}
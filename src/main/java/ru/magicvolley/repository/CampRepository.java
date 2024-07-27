package ru.magicvolley.repository;

import ru.magicvolley.entity.CampEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CampRepository extends JpaRepository<CampEntity, UUID> {

}

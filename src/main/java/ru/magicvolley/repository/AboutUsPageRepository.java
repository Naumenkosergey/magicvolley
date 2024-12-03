package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.AboutUsPageEntity;

import java.util.UUID;

@Repository
public interface AboutUsPageRepository extends JpaRepository<AboutUsPageEntity, UUID> {
}
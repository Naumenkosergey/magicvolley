package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.magicvolley.entity.HomePageEntity;

import java.util.UUID;

public interface HomePageRepository extends JpaRepository<HomePageEntity, UUID> {
}
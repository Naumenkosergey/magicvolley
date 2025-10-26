package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.HomePageEntity;

import java.util.UUID;

@Repository
public interface HomePageRepository extends JpaRepository<HomePageEntity, UUID> {
}
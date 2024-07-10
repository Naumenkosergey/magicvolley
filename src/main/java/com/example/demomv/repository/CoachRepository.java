package com.example.demomv.repository;

import com.example.demomv.entity.CoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, UUID> {
}

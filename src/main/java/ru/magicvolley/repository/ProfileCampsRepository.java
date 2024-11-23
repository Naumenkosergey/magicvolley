package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.ProfileCampsEntity;

@Repository
public interface ProfileCampsRepository extends JpaRepository<ProfileCampsEntity, ProfileCampsEntity.Id> {
}
package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.CampUserEntity;
import ru.magicvolley.repository.projection.NotificationProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampUserRepository extends JpaRepository<CampUserEntity, CampUserEntity.Id> {

    List<CampUserEntity> findByIdCampId(UUID campId);

    @Query("SELECT new ru.magicvolley.repository.projection.NotificationProjection(cu.id.campId, COUNT(cu)) " +
            "FROM CampUserEntity cu WHERE cu.isViewed = false group by cu.id.campId")
    List<NotificationProjection> findAllNotificationProjection();

    Integer countByIsViewedFalse();

    List<CampUserEntity> findAllByIdCampId(UUID uuid);
}
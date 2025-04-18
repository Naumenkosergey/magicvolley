package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.enums.TypeEntity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface MediaStorageRepository extends JpaRepository<MediaStorageEntity, UUID> {

    List<MediaStorageEntity> findAllByEntityIdIn(Collection<UUID> ids);

    @Query("select m from MediaStorageEntity m where m.id in ?1 and m.entityId = ?2 and m.typeEntity = ?3")
    List<MediaStorageEntity> findAllByIdInAndEntityIdAndTypeEntity(Collection<UUID> ids, UUID entityId, TypeEntity typeEntity);

    List<MediaStorageEntity> findAllByEntityIdAndTypeEntity(UUID entityId, TypeEntity typeEntity);

    List<MediaStorageEntity> findAllByEntityIdInAndTypeEntity(Collection<UUID> entityIds, TypeEntity typeEntity);

    @Query("SELECT m FROM MediaStorageEntity m INNER JOIN CampEntity c ON m.entityId = c.id " +
            "WHERE m.typeEntity = :typeEntity " +
            "ORDER BY c.dateEnd DESC")
    List<MediaStorageEntity> findAllByTypeEntity(TypeEntity typeEntity);

}
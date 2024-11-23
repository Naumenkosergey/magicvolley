package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.MediaStorageEntity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface MediaStorageRepository extends JpaRepository<MediaStorageEntity, UUID> {

    List<MediaStorageEntity> findAllByEntityIdIn(Collection<UUID> ids);
}
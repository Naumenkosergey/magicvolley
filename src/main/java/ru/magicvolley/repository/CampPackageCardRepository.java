package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.CampPackageCardEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampPackageCardRepository extends JpaRepository<CampPackageCardEntity, CampPackageCardEntity.Id> {

    List<CampPackageCardEntity> findAllByIdCampId(UUID campId);

}

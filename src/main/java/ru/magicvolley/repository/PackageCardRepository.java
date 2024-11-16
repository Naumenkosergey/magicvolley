package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.PackageCardEntity;

@Repository
public interface PackageCardRepository extends JpaRepository<PackageCardEntity, Integer> {

}

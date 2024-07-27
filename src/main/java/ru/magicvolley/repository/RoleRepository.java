package ru.magicvolley.repository;

import ru.magicvolley.entity.RoleEntity;
import ru.magicvolley.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByRole(Role role);
}

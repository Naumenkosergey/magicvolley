package ru.magicvolley.repository;

import ru.magicvolley.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByLogin(String login);

    Boolean existsByLogin(String login);

    Boolean existsByEmail(String email);
}

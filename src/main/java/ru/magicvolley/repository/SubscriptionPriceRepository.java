package ru.magicvolley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magicvolley.entity.SubscriptionPriceEntity;

import java.util.UUID;

@Repository
public interface SubscriptionPriceRepository extends JpaRepository<SubscriptionPriceEntity, UUID> {
}
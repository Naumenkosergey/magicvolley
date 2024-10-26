package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "subscription_prices")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscriptionPriceEntity {
    @Id
    private UUID id;
    private String title;
    private String subTitle;
    private Double price;


    private UUID subscriptionId;

    @ManyToOne
    @JoinColumn(name = "subscriptionId", insertable = false, updatable = false)
    private SubscriptionEntity subscription;
}

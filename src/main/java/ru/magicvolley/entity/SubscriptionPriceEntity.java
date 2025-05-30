package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "subscription_prices")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SubscriptionPriceEntity {
    @Id
    private UUID id;
    private String title;
    private String subTitle;
    private Double price;


    private UUID subscriptionId;
    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "subscriptionId", insertable = false, updatable = false)
    private SubscriptionEntity subscription;
}

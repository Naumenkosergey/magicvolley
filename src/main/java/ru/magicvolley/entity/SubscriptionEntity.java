package ru.magicvolley.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscriptionEntity {
    @Id
    private UUID id;
    private String subscriptionName;
    private Long version;
    private Integer orderNumber;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "subscriptionId", insertable = false, updatable = false)
//    private List<SubscriptionPriceEntity> prices;
}

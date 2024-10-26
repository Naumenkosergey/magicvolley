package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.magicvolley.entity.SubscriptionEntity;
import ru.magicvolley.entity.SubscriptionPriceEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubscriptionPriceResponse {

    private UUID id;
    private String name;
    private List<Price> prices;

    public SubscriptionPriceResponse(Map.Entry<UUID, List<SubscriptionPriceEntity>> entry,
                                     Map<UUID, SubscriptionEntity> subscriptionToSubscription ) {
        this.id = entry.getKey();
        this.name = subscriptionToSubscription.get(entry.getKey()).getSubscriptionName();
        this.prices = entry.getValue().stream()
                .map(Price::new)
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Price {
        private UUID id;
        private String title;
        private String subTitle;
        private double price;

        public Price(SubscriptionPriceEntity subscriptionPrice) {
            this.id = subscriptionPrice.getId();
            this.title = subscriptionPrice.getTitle();
            this.subTitle = subscriptionPrice.getSubTitle();
            this.price = subscriptionPrice.getPrice();
        }
    }
}

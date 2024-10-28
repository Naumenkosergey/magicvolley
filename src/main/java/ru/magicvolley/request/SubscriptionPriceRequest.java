package ru.magicvolley.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPriceRequest {

    private UUID id;
    private String subscriptionName;
    private List<Price> prices;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Price {
        private UUID id;
        private String title;
        private String subTitle;
        private double price;
    }
}

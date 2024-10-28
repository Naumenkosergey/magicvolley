package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.entity.SubscriptionEntity;
import ru.magicvolley.entity.SubscriptionPriceEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.SubscriptionPriceRepository;
import ru.magicvolley.repository.SubscriptionRepository;
import ru.magicvolley.request.SubscriptionPriceRequest;
import ru.magicvolley.response.SubscriptionPriceResponse;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionPriceService {

    private final SubscriptionPriceRepository subscriptionPriceRepository;
    private final SubscriptionRepository ssubscriptionRepository;

    @Transactional(readOnly = true)
    public List<SubscriptionPriceResponse> getSubscriptionPrice() {

        var subscriptionIdToSubscriptionPrice = subscriptionPriceRepository.findAll()
                .stream().collect(Collectors.groupingBy(SubscriptionPriceEntity::getSubscriptionId));

        Map<UUID, SubscriptionEntity> subscriptionToSubscription = ssubscriptionRepository.findAllById(subscriptionIdToSubscriptionPrice.keySet()).stream()
                .collect(Collectors.toMap(SubscriptionEntity::getId, Function.identity()));

        return subscriptionIdToSubscriptionPrice.entrySet().stream()
                .map(entry -> new SubscriptionPriceResponse(entry, subscriptionToSubscription))
                .sorted(Comparator.comparing(o -> subscriptionToSubscription.get(o.getId()).getOrderNumber()))
                .collect(Collectors.toList());
    }

    @Transactional
    public UUID createSubscriptionPrice(SubscriptionPriceRequest subscriptionPriceRequest) {

        Integer maxOrder = ssubscriptionRepository.findAll().stream()
                .map(SubscriptionEntity::getOrderNumber)
                .max(Comparator.naturalOrder())
                .orElse(0);
        SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                .id(UUID.randomUUID())
                .subscriptionName(subscriptionPriceRequest.getSubscriptionName())
                .orderNumber(maxOrder + 1)
                .build();
        ssubscriptionRepository.save(subscriptionEntity);

        if (CollectionUtils.isNotEmpty(subscriptionPriceRequest.getPrices())) {
            List<SubscriptionPriceEntity> subscriptionPrices = subscriptionPriceRequest.getPrices().stream()
                    .map(price -> SubscriptionPriceEntity.builder()
                            .id(UUID.randomUUID())
                            .title(price.getTitle())
                            .subTitle(price.getSubTitle())
                            .price(price.getPrice())
                            .subscriptionId(subscriptionEntity.getId())
                            .build()
                    ).collect(Collectors.toList());
            subscriptionPriceRepository.saveAll(subscriptionPrices);
        }
        return subscriptionEntity.getId();
    }

    @Transactional
    public UUID updateSubscriptionPrice(SubscriptionPriceRequest subscriptionPriceRequest) {

        if (Objects.nonNull(subscriptionPriceRequest.getId())) {
            SubscriptionEntity subscriptionFromDb = ssubscriptionRepository.findById(subscriptionPriceRequest.getId())
                    .orElseThrow(() -> new EntityNotFoundException(SubscriptionEntity.class, subscriptionPriceRequest.getId()));
            subscriptionFromDb.setSubscriptionName(subscriptionPriceRequest.getSubscriptionName());
        }

        if (CollectionUtils.isNotEmpty(subscriptionPriceRequest.getPrices())) {
            Map<UUID, SubscriptionPriceRequest.Price> mapPriceIdToPrice = subscriptionPriceRequest.getPrices().stream()
                    .filter(price -> Objects.nonNull(price.getId()))
                    .collect(Collectors.toMap(SubscriptionPriceRequest.Price::getId, Function.identity()));
            List<SubscriptionPriceEntity> pricesFromDb = subscriptionPriceRepository.findAllById(mapPriceIdToPrice.keySet());
            pricesFromDb.forEach(priceFromDb -> {
                var priceFromRequest = mapPriceIdToPrice.get(priceFromDb.getId());
                priceFromDb.setTitle(priceFromRequest.getTitle());
                priceFromDb.setSubTitle(priceFromRequest.getSubTitle());
                priceFromDb.setPrice(priceFromRequest.getPrice());
                priceFromDb.setSubscriptionId(subscriptionPriceRequest.getId());
                subscriptionPriceRepository.save(priceFromDb);
            });
        }
        return subscriptionPriceRequest.getId();
    }
}

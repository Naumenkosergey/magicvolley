package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.entity.SubscriptionEntity;
import ru.magicvolley.entity.SubscriptionPriceEntity;
import ru.magicvolley.repository.SubscriptionPriceRepository;
import ru.magicvolley.repository.SubscriptionRepository;
import ru.magicvolley.response.SubscriptionPriceResponse;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
}

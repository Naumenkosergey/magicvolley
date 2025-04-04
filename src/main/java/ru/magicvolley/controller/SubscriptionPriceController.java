package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.request.SubscriptionPriceRequest;
import ru.magicvolley.response.SubscriptionPriceResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.SubscriptionPriceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/magicvolley/price")
@AllArgsConstructor
public class SubscriptionPriceController {

    private final SubscriptionPriceService subscriptionPriceService;

    @GetMapping()
    public ApiResponse<List<SubscriptionPriceResponse>> getSubscriptionPrice() {

        return new ApiResponse<>(subscriptionPriceService.getSubscriptionPrice());
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<UUID> createSubscriptionPrice(@RequestBody SubscriptionPriceRequest subscriptionPriceRequest) {

        return new ApiResponse<>(subscriptionPriceService.createSubscriptionPrice(subscriptionPriceRequest));
    }

    @PutMapping("/{subscriptionPriceId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    public ApiResponse<UUID> updateSubscriptionPrice(@RequestBody SubscriptionPriceRequest subscriptionPriceRequest,
                                                     @PathVariable UUID subscriptionPriceId) {

        return new ApiResponse<>(subscriptionPriceService.updateSubscriptionPrice(subscriptionPriceRequest, subscriptionPriceId));
    }

    @DeleteMapping("/{subscriptionPriceId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean> updateSubscriptionPrice(@PathVariable UUID subscriptionPriceId) {

        return new ApiResponse<>(subscriptionPriceService.deleteSubscriptionPrice(subscriptionPriceId));
    }
}

package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magicvolley.response.SubscriptionPriceResponse;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.SubscriptionPriceService;

import java.util.List;

@RestController
@RequestMapping("/magicvolley/price")
@AllArgsConstructor
public class SubscriptionPriceController {

    private final SubscriptionPriceService subscriptionPriceService;

    @GetMapping()
    public ApiResponse<List<SubscriptionPriceResponse>> getSubscriptionPrice() {

        return new ApiResponse<>(subscriptionPriceService.getSubscriptionPrice());
    }
}

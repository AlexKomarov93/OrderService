package com.example.OrderService.services.orderProcessingService;

import com.example.OrderService.grpc.ProductsResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessingValidator {

    public void checkFieldsAreNotNull(Long productId, Integer quantity, Long userId) {
        if (productId == null || quantity == null || userId == null) {
            throw new IllegalArgumentException("Параметры заказа не могут быть null");
        }
    }

    public void checkQuantity(Integer quantity, ProductsResponse product) {
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Товара на складе недостаточно");
        }
    }
}

package com.example.OrderService.services.orderProcessingService;

import com.example.OrderService.dto.event.OrderEvent;
import com.example.OrderService.grpc.ProductsResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderEventMapper {

    public OrderEvent toEvent(ProductsResponse product, Long orderId, Long productId, Integer quantity, Long userId, BigDecimal totalPrice) {
        return OrderEvent.builder()
                .orderId(orderId)
                .productId(productId)
                .productName(product.getName())
                .quantity(quantity)
                .price(new BigDecimal(product.getPrice()))
                .sale(new BigDecimal(product.getSale()))
                .totalPrice(totalPrice)
                .userId(userId)
                .build();
    }
}


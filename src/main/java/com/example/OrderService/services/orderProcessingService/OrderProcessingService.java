package com.example.OrderService.services.orderProcessingService;

import com.example.OrderService.dto.event.OrderEvent;
import com.example.OrderService.grpc.InventoryClient;
import com.example.OrderService.grpc.ProductsResponse;
import com.example.OrderService.services.kafkaProducer.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderProcessingService {

    private final OrderEventProducer orderEventProducer;
    private final InventoryClient inventoryClient;
    private final OrderProcessingValidator validator;
    private final OrderEventMapper orderEventMapper;

    public void createOrder(Long productId, Integer quantity, Long userId) {
        validator.checkFieldsAreNotNull(productId, quantity, userId);

        var responses = inventoryClient.checkProductAvailability(productId, "");
        ProductsResponse product = responses.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Товар не найден на складе"));

        validator.checkQuantity(quantity, product);

        Long orderId = generateOrderId();
        BigDecimal totalPrice = calculateTotalPrice(product, quantity);

        OrderEvent orderEvent = orderEventMapper.toEvent(product, orderId, productId, quantity, userId, totalPrice);

        orderEventProducer.sendOrder(orderEvent);
    }

    private BigDecimal calculateTotalPrice(ProductsResponse product, Integer quantity) {
        BigDecimal price = new BigDecimal(product.getPrice());
        BigDecimal sale = new BigDecimal(product.getSale());

        return price.subtract(sale).multiply(BigDecimal.valueOf(quantity));
    }

    private Long generateOrderId() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }
}


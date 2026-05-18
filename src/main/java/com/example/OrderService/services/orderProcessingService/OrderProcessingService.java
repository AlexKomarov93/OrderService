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

    public void createOrder(Long productId, Integer quantity, Long userId) {
        checkFieldsAreNotNull(productId, quantity, userId);

        var responses = inventoryClient.checkProductAvailability(productId, "");
        ProductsResponse product = responses.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Товар не найден на складе"));

        checkQuantity(quantity, product);

        Long orderId = generateOrderId();
        BigDecimal totalPrice = calculateTotalPrice(product, quantity);

        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(orderId)
                .productId(productId)
                .productName(product.getName())
                .quantity(quantity)
                .price(new BigDecimal(product.getPrice()))
                .sale(new BigDecimal(product.getSale()))
                .totalPrice(totalPrice)
                .userId(userId)
                .build();

        orderEventProducer.sendOrder(orderEvent);
    }

    private void checkFieldsAreNotNull(Long productId, Integer quantity, Long userId) {
        if (productId == null || quantity == null || userId == null) {
            throw new IllegalArgumentException("Параметры заказа не могут быть null");
        }
    }

    private void checkQuantity(Integer quantity, ProductsResponse product) {
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Товара на складе недостаточно");
        }
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

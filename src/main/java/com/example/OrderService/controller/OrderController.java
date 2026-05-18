package com.example.OrderService.controller;

import com.example.OrderService.dto.request.OrderRequest;
import com.example.OrderService.entity.Users;
import com.example.OrderService.services.orderProcessingService.OrderProcessingService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/OrderService")
public class OrderController {

    private final OrderProcessingService orderProcessingService;

    @PostMapping("/order")
    public String createOrder(@RequestBody OrderRequest request, @AuthenticationPrincipal Users user) {
        orderProcessingService.createOrder(
                request.getProductId(),
                request.getQuantity(),
                user.getId()
        );
        return "Заказ успешно сформирован и отправлен в Kafka!";
    }

}


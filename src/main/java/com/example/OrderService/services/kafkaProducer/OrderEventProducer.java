package com.example.OrderService.services.kafkaProducer;

import com.example.OrderService.dto.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    @Value("${topic.send-order}")
    private String sendClientTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrder(OrderEvent orderSendEvent) {
        kafkaTemplate.send(sendClientTopic, orderSendEvent.getOrderId().toString(), orderSendEvent);
    }
}


package com.example.OrderService.grpc;

import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class InventoryClient {

    private final InventoryServiceGrpc.InventoryServiceBlockingStub blockingStub;

    public InventoryClient(GrpcChannelFactory channelFactory) {
        var channel = channelFactory.createChannel("inventory-service");
        this.blockingStub = InventoryServiceGrpc.newBlockingStub(channel);
    }

    public List<ProductsResponse> checkProductAvailability(Long productId, String productName) {
        ProductsRequest request = ProductsRequest.newBuilder()
                .setId(productId)
                .setName(productName)
                .build();

        List<ProductsResponse> responses = new ArrayList<>();

        try {
            Iterator<ProductsResponse> responseIterator = blockingStub.checkAvailability(request);

            while (responseIterator.hasNext()) {
                responses.add(responseIterator.next());
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка gRPC при вызове Inventory Service: " + e.getMessage(), e);
        }

        return responses;
    }
}

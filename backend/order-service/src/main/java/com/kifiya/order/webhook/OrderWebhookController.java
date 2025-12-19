package com.kifiya.order.webhook;

import com.kifiya.order.models.Order;
import com.kifiya.order.service.OrderService;
import com.kifiya.order.webhook.dto.OrderWebhookRequest;
import com.kifiya.order.webhook.dto.OrderWebhookResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/orders")
public class OrderWebhookController {

    private final OrderService orderService;

    public OrderWebhookController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderWebhookResponse> receiveOrderDetails(
            @Valid @RequestBody OrderWebhookRequest request,
            @RequestHeader(value = "X-Webhook-Source", required = false) String webhookSource) {

        Order order = orderService.processWebhookOrder(request);

        OrderWebhookResponse response = new OrderWebhookResponse(
                order.getId(),
                order.getStatus(),
                "Order details received and processed successfully",
                webhookSource
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}


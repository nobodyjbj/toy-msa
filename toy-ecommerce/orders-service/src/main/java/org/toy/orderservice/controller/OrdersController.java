package org.toy.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.toy.orderservice.messagequeue.KafkaProducer;
import org.toy.orderservice.messagequeue.OrderProducer;
import org.toy.orderservice.service.OrdersService;
import org.toy.orderservice.vo.RequestOrders;
import org.toy.orderservice.vo.ResponseOrders;

import java.util.List;

@RestController
@RequestMapping("/orders-service/")
@RequiredArgsConstructor
public class OrdersController {
    private final Environment environment;
    private final OrdersService ordersService;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

    @GetMapping("/health-check")
    public String healthCheck() {
        return String.format("It's working in OrdersService on PORT %s", environment.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrders> createOrders(@PathVariable("userId") String userId, @RequestBody RequestOrders requestOrders) {
        // JPA
        // ResponseOrders orders = ordersService.createOrders(userId, requestOrders);

        // Kafka
        ResponseOrders orders = ResponseOrders.of(userId, requestOrders);
        kafkaProducer.send("example-catalog-topic", orders);
        orderProducer.send("orders", orders);
        return ResponseEntity.status(HttpStatus.CREATED).body(orders);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrders>> getOrders(@PathVariable("userId") String userId) {
        List<ResponseOrders> orders = ordersService.getOrdersByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}

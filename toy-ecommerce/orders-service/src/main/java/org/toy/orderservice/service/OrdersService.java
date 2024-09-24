package org.toy.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.toy.orderservice.entity.OrdersEntity;
import org.toy.orderservice.repository.OrdersRepository;
import org.toy.orderservice.vo.RequestOrders;
import org.toy.orderservice.vo.ResponseOrders;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;

    public ResponseOrders createOrders(String userId, RequestOrders requestOrder) {
        OrdersEntity ordersEntity = ordersRepository.save(OrdersEntity.of(userId, requestOrder));
        return ResponseOrders.of(ordersEntity);
    }

    public ResponseOrders getOrderByOrderId(String orderId) {
        OrdersEntity ordersEntity = ordersRepository.findByOrderId(orderId).orElseThrow(() ->
                new NoSuchElementException("Not found order with orderId: " + orderId));
        return ResponseOrders.of(ordersEntity);
    }

    public List<ResponseOrders> getOrdersByUserId(String userId) {
        log.info("Add retrieved orders data");
        List<OrdersEntity> ordersEntities = ordersRepository.findByUserId(userId);
        return ResponseOrders.of(ordersEntities);
    }
}

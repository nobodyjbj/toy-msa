package org.toy.orderservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.toy.orderservice.entity.OrdersEntity;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends CrudRepository<OrdersEntity, Long> {
    Optional<OrdersEntity> findByOrderId(String orderId);
    List<OrdersEntity> findByUserId(String userId);
}

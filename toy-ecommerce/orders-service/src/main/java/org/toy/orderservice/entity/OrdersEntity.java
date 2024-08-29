package org.toy.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.toy.orderservice.vo.RequestOrders;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrdersEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 120)
    private String productId;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Integer unitPrice;
    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false, unique = true)
    private String orderId;

    @CreatedDate
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP(6)")
    private LocalDateTime createdAt;

    public static OrdersEntity of(String userId, RequestOrders requestOrder) {
        return OrdersEntity.builder()
                .productId(requestOrder.getProductId())
                .quantity(requestOrder.getQuantity())
                .unitPrice(requestOrder.getUnitPrice())
                .totalPrice(requestOrder.getUnitPrice() * requestOrder.getQuantity())
                .userId(userId)
                .orderId(UUID.randomUUID().toString())
                .build();
    }
}

package org.toy.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.toy.orderservice.entity.OrdersEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrders {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createAt;

    private String orderId;

    public static ResponseOrders of(OrdersEntity ordersEntity) {
        return ResponseOrders.builder()
                .productId(ordersEntity.getProductId())
                .quantity(ordersEntity.getQuantity())
                .unitPrice(ordersEntity.getUnitPrice())
                .totalPrice(ordersEntity.getTotalPrice())
                .createAt(LocalDateTime.now())
                .orderId(ordersEntity.getOrderId())
                .build();
    }

    public static List<ResponseOrders> of(Iterable<OrdersEntity> orders) {
        List<ResponseOrders> responseOrders = new ArrayList<>();
        orders.forEach(o -> responseOrders.add(ResponseOrders.of(o)));
        return responseOrders;
    }
}

package org.toy.orderservice.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestOrders {
    @NotNull(message = "productId cannot be null")
    private String productId;
    @NotNull(message = "quantity cannot be null")
    private Integer quantity;
    @NotNull(message = "unitPrice cannot be null")
    private Integer unitPrice;
}

package org.toy.orderservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Payload {
    private String order_id;
    private String user_id;
    private String product_id;
    private int quantity;
    private int unit_price;
    private int total_price;
}

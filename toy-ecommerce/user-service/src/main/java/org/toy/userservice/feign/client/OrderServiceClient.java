package org.toy.userservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.toy.userservice.vo.ResponseOrder;

import java.util.List;

@FeignClient(name = "orders-service")
public interface OrderServiceClient {

    @GetMapping("/orders-service/{userId}/orders")
    List<ResponseOrder> getOrders(@PathVariable("userId") String userId);
}

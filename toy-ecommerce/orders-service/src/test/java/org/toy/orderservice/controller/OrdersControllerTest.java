package org.toy.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.toy.orderservice.vo.RequestOrders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrdersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    private final MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8
    );

    @Test
    void healthCheck() throws Exception {
        mockMvc.perform(
                get("/orders-service/health-check")
                        .contentType(contentType)
                        .accept(contentType)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void createOrders() throws Exception {
        String userId = "53233df4-6503-4f1c-8921-80b8692dc600";
        RequestOrders orders = new RequestOrders();
        orders.setProductId("CATALOG-001");
        orders.setQuantity(10);
        orders.setUnitPrice(1000);

        mockMvc.perform(
                    post("/orders-service/{userId}/orders", userId)
                            .content(objectMapper.writeValueAsString(orders))
                            .contentType(contentType)
                            .accept(contentType)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void getOrders() throws Exception {
        String userId = "53233df4-6503-4f1c-8921-80b8692dc600";
        mockMvc.perform(
                    get("/orders-service/{userId}/orders", userId)
                            .contentType(contentType)
                            .accept(contentType)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}
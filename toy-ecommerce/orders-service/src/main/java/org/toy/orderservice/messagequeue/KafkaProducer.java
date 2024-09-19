package org.toy.orderservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.toy.orderservice.vo.ResponseOrders;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(String topic, ResponseOrders responseOrders) {
        String jsonInString = "";
        try {
            jsonInString = objectMapper.writeValueAsString(responseOrders);
        } catch(JsonProcessingException ex) {
            log.error(ex.getMessage());
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Order Producer sent data from the Order microservice: {}", jsonInString);
    }
}

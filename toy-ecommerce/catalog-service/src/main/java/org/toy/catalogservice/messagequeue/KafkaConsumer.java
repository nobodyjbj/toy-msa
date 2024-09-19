package org.toy.catalogservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.toy.catalogservice.entity.CatalogEntity;
import org.toy.catalogservice.repository.CatalogRepository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final CatalogRepository catalogRepository;

    @KafkaListener(topics = "example-catalog-topic")
    public void updateQuantity(String kafkaMessage) {
        log.info("Kafka Message: -> {}", kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        CatalogEntity entity = catalogRepository.findByProductId((String)map.get("productId")).orElseThrow();
        entity.setStock(entity.getStock() - (Integer)map.get("quantity"));
        catalogRepository.save(entity);
    }

}

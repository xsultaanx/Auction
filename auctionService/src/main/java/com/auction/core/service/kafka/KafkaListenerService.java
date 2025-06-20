package com.auction.core.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.auction.common.events.AuctionCreateEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaListenerService {

    private final KafkaEventHandler kafkaEventHandler;

    @KafkaListener(topics = "${spring.kafka.topics.consume.quartzFinished}")
    public void listen(ConsumerRecord<?, ?> record){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json = record.value().toString();
            AuctionCreateEvent event = mapper.readValue(json, AuctionCreateEvent.class);
            log.info("AuctionCreateEvent: {}", event);
            kafkaEventHandler.handle(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

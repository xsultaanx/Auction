package com.auction.core.service.events;

import com.auction.core.service.kafka.KafkaSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.auction.common.events.AuctionCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuctionEventListener {

    @Value("${spring.kafka.topics.produce.auctionCreate}")
    private String auctionCreateTopic;
    private final KafkaSender kafkaSender;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuctionEventListener(KafkaSender kafkaSender, ObjectMapper objectMapper) {
        this.kafkaSender = kafkaSender;
        this.objectMapper = objectMapper;
    }

    @EventListener
    public void handleUserRegistered(AuctionCreateEvent event) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(event);
        log.info("AuctionCreateEvent received: {}", json);
        kafkaSender.send(auctionCreateTopic, json);
    }
}

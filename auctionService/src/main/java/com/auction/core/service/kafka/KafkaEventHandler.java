package com.auction.core.service.kafka;

import com.auction.core.service.auctionItem.AuctionService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.auction.common.events.AuctionCreateEvent;
import org.springframework.stereotype.Component;
import org.auction.common.enums.AuctionEventType;

@Component
@AllArgsConstructor
public class KafkaEventHandler {

    private final AuctionService auctionService;

    @SneakyThrows
    public void handle(AuctionCreateEvent auctionCreateEvent) {

        switch (auctionCreateEvent.getEventType()){
            case AuctionEventType.FINISHED:
                auctionService.updateStatus(auctionCreateEvent);
                break;
        }
    }
    
}

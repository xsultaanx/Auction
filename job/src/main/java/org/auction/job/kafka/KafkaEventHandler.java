package org.auction.job.kafka;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.auction.common.enums.AuctionEventType;
import org.auction.common.events.AuctionCreateEvent;
import org.auction.job.service.Service;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaEventHandler {
    private final Service service;

    @SneakyThrows
    public void handle(AuctionCreateEvent auctionCreateEvent) {
        switch (auctionCreateEvent.getEventType()){
            case AuctionEventType.CREATED:
                service.scheduleAuctionEnd(auctionCreateEvent);
                break;
        }

    }

}

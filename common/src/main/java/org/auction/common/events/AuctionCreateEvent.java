package org.auction.common.events;

import lombok.Data;
import org.auction.common.enums.AuctionEventType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AuctionCreateEvent {
    private UUID auctionId;
    private AuctionEventType eventType;
    private LocalDateTime endTime;

}

package org.auction.common.web.dto.request;

import lombok.Data;
import org.auction.common.entity.AuctionItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BidDto {
    private UUID uuid;
    private BigDecimal amount; // Сумма ставки
    private LocalDateTime timestamp; // Время ставки
    private UUID userId;
    private AuctionItem auctionItem;
}

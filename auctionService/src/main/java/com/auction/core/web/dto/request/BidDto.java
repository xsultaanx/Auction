package com.auction.core.web.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BidDto {
    private BigDecimal amount;
    private UUID auctionItemId;
    private UUID userId;
}

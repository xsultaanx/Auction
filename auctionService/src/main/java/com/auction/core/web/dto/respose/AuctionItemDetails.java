package com.auction.core.web.dto.respose;

import jakarta.persistence.*;
import lombok.Data;
import org.auction.common.entity.AuctionItem;
import org.auction.common.entity.Bid;
import org.auction.common.entity.Category;
import org.auction.common.entity.ItemImage;
import org.auction.common.enums.AuctionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class AuctionItemDetails {
    private UUID id;

    private String title;
    private String description;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private BigDecimal minStepPrice;

    private AuctionStatus status;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private UUID winner;

    private UUID userId;

    private int bidsCount;

    private List<Bid> bids;

    private List<ItemImage> itemImages;

    private Category category;



}

package org.auction.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.auction.common.enums.AuctionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "auction_items")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class AuctionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String description;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private BigDecimal minStepPrice;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private UUID userId;

    @OneToMany(mappedBy = "auctionItem", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Bid> bids;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_item_id")
    private List<ItemImage> itemImages;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}

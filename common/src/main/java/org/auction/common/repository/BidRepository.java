package org.auction.common.repository;

import org.auction.common.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BidRepository extends JpaRepository<Bid, UUID> {
    List<Bid> findByAuctionItem_Id(UUID auctionItemId);
    Optional<Bid> findTopByAuctionItem_IdOrderByAmountDesc(UUID auctionItemId);
    @Query("SELECT MAX(b.amount) FROM Bid b WHERE b.auctionItem.id = :auctionItemId")
    BigDecimal findMaxBidAmountByAuctionItemId(@Param("auctionItemId") UUID auctionItemId);
}

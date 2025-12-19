package org.auction.common.repository;

import org.auction.common.entity.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuctionItemRepository extends JpaRepository<AuctionItem, UUID> {
    List<AuctionItem> findAllByWinner(UUID winner);
    @Query("SELECT ai FROM AuctionItem ai LEFT JOIN FETCH ai.bids WHERE ai.id = :id")
    Optional<AuctionItem> findByIdWithBids(@Param("id") UUID id);
}

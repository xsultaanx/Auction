package org.auction.common.repository;

import org.auction.common.entity.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuctionItemRepository extends JpaRepository<AuctionItem, UUID> {
}

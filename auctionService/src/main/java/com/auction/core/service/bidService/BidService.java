package com.auction.core.service.bidService;

import com.auction.core.web.dto.request.BidDto;
import org.auction.common.entity.Bid;
import org.auction.common.service.CommandService;

import java.util.List;
import java.util.UUID;

public interface BidService extends CommandService<Bid> {
    Bid placeBid(BidDto bidDto);
    List<Bid> getBidsForAuction(UUID auctionId);
}

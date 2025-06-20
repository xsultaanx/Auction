package com.auction.core.service.auctionItem;

import org.auction.common.entity.AuctionItem;
import org.auction.common.events.AuctionCreateEvent;
import org.auction.common.service.CommandService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface AuctionService extends CommandService<AuctionItem>{
    void create(AuctionItem auctionItem, List<MultipartFile> files);
    boolean checkAuctionActive(AuctionItem auctionItem);
    void updateStatus(AuctionCreateEvent auctionEvent);
}

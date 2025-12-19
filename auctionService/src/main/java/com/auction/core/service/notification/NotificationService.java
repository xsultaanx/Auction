package com.auction.core.service.notification;

import org.auction.common.entity.AuctionItem;
import org.auction.common.repository.AuctionItemRepository;
import org.auction.common.service.user.UserAuthentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NotificationService {

    private final UserAuthentication userAuthentication;
    private final AuctionItemRepository auctionItemRepository;

    public NotificationService(UserAuthentication userAuthentication, AuctionItemRepository auctionItemRepository) {
        this.userAuthentication = userAuthentication;
        this.auctionItemRepository = auctionItemRepository;
    }

    public List<AuctionItem> getAuctionItems() {
        List<AuctionItem> auctionItems = auctionItemRepository.findAllByWinner(userAuthentication.getUserId());
        Collections.reverse(auctionItems);
        return auctionItems;
    }

}

package com.auction.core.web.testController;

import lombok.RequiredArgsConstructor;
import org.auction.common.entity.AuctionItem;
import org.auction.common.repository.AuctionItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class AuctionItemControllerTest {
    private final AuctionItemRepository auctionItemRepository;

    @GetMapping
    public List<AuctionItem> getAllAuctionItem() {
        List<AuctionItem> auctionItems = auctionItemRepository.findAll();
        Collections.reverse(auctionItems);
        return auctionItems;
    }

    @GetMapping("/{id}")
    public AuctionItem getAuctionItemById(@PathVariable UUID id) {
        return auctionItemRepository.findById(id).orElse(null);
    }
}

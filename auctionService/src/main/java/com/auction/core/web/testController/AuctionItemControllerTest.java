package com.auction.core.web.testController;

import com.auction.core.web.dto.respose.AuctionItemDetails;
import lombok.RequiredArgsConstructor;
import org.auction.common.entity.AuctionItem;
import org.auction.common.enums.AuctionStatus;
import org.auction.common.repository.AuctionItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class AuctionItemControllerTest {
    private final AuctionItemRepository auctionItemRepository;

    @GetMapping
    public List<AuctionItemDetails> getAllAuctionItem() {
        List<AuctionItemDetails> detailsList = auctionItemRepository.findAll().stream()
                .map(this::mapToDetails)
                .collect(Collectors.toList());
//        auctionItems.stream()
//                .filter(auctionItem ->
//                        auctionItem.getStatus() != AuctionStatus.ACTIVE &&
//                                !auctionItem.getEndTime().isBefore(LocalDateTime.now())
//                );
        Collections.reverse(detailsList);
        return detailsList;
    }

    @GetMapping("/{id}")
    public AuctionItem getAuctionItemById(@PathVariable UUID id) {
        return auctionItemRepository.findById(id).orElse(null);
    }


    private AuctionItemDetails mapToDetails(AuctionItem item) {
        AuctionItemDetails details = new AuctionItemDetails();
        details.setId(item.getId());
        details.setTitle(item.getTitle());
        details.setDescription(item.getDescription());
        details.setStartingPrice(item.getStartingPrice());
        details.setCurrentPrice(item.getCurrentPrice());
        details.setMinStepPrice(item.getMinStepPrice());
        details.setStatus(item.getStatus());
        details.setStartTime(item.getStartTime());
        details.setEndTime(item.getEndTime());
        details.setWinner(item.getWinner());
        details.setUserId(item.getUserId());
        details.setBidsCount(item.getBids() != null ? item.getBids().size() : 0);
        details.setBids(item.getBids());
        details.setItemImages(item.getItemImages());
        details.setCategory(item.getCategory());
        return details;
    }
}

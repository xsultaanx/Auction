package com.auction.core.web.testController;

import com.auction.core.service.bidService.BidServiceImpl;
import com.auction.core.web.dto.request.BidDto;
import lombok.RequiredArgsConstructor;
import org.auction.common.entity.Bid;
import org.auction.common.repository.BidRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/bidTest")
@RequiredArgsConstructor
public class BidControllerTest {
    private final BidServiceImpl bidServiceImpl;
    private final BidRepository bidRepository;
    @PostMapping
    public Bid bid(@ModelAttribute BidDto bid) {
        return bidServiceImpl.placeBid(bid);
    }

    @GetMapping("/{id}")
    public List<Bid> getBidById(@PathVariable UUID id) {
        return bidRepository.findByAuctionItem_Id(id);
    }

    @GetMapping("/getMax/{id}")
    public Optional<Bid> getMax(@PathVariable UUID id) {
        Optional<Bid> bid = bidRepository.findTopByAuctionItem_IdOrderByAmountDesc(id);
        return bid;
    }
}

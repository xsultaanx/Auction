package com.auction.core.web.contoller;

import com.auction.core.service.bidService.BidService;
import com.auction.core.web.dto.request.BidDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/bid")
public class BidController {
    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping("/")
    public ResponseEntity<?> uploadAuctionWithParam(@ModelAttribute BidDto dto) {
        bidService.placeBid(dto);
        return ResponseEntity.ok().body("success");
    }
}

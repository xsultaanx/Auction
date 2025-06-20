package com.auction.core.web.contoller;

import com.auction.core.service.auctionItem.AuctionService;
import lombok.AllArgsConstructor;
import org.auction.common.entity.AuctionItem;
import org.auction.common.web.dto.request.AuctionItemDto;
import org.auction.common.web.mapper.AuctionItemMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/uploadAuction")
@AllArgsConstructor
public class AuctionItemUploadController {
    private final AuctionService auctionService;
    private final AuctionItemMapper auctionItemMapper;

    @PostMapping("/withParam")
    public ResponseEntity<?> uploadAuctionWithParam(@ModelAttribute AuctionItemDto dto,
                                                    @RequestPart("files") List<MultipartFile> files) {
        AuctionItem auctionItem = auctionItemMapper.fromDto(dto);
        auctionService.create(auctionItem,files);
        return ResponseEntity.ok().body("success");
    }
    @PostMapping("/")
    public ResponseEntity<?> uploadAuction(@RequestBody AuctionItemDto dto,  @RequestPart("files") List<MultipartFile> files) {
        AuctionItem auctionItem = auctionItemMapper.fromDto(dto);
        auctionService.create(auctionItem,files);
        return ResponseEntity.ok().body("success");
    }
}

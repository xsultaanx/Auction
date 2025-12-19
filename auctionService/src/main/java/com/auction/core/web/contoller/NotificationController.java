package com.auction.core.web.contoller;

import com.auction.core.service.notification.NotificationService;
import org.auction.common.entity.AuctionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<AuctionItem> notification() {
        return notificationService.getAuctionItems();
    }
}

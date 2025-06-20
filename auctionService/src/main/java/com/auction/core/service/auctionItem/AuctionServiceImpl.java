package com.auction.core.service.auctionItem;

import lombok.RequiredArgsConstructor;
import org.auction.common.entity.AuctionItem;
import org.auction.common.entity.ItemImage;
import org.auction.common.enums.AuctionEventType;
import org.auction.common.enums.AuctionStatus;
import org.auction.common.events.AuctionCreateEvent;
import org.auction.common.repository.AuctionItemRepository;
import org.auction.common.service.image.ImageService;
import org.auction.common.service.user.UserAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionItemRepository auctionItemRepository;
    private final UserAuthentication userAuthentication;
    private final ImageService imageService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${minio.buckets.auction-image-bucket}")
    private String bucketName;

//    @Value("${minio.url}")
//    private String minioUrl;
    private String minioUrl = "http://172.20.10.3:9000";

    public void create(AuctionItem auctionItem, List<MultipartFile> files) {
        List<ItemImage> itemImages = files.stream()
                .map(file -> {
                    ItemImage itemImage = new ItemImage();
                    itemImage.setImageUrl(imageService.uploadImage(file,bucketName,minioUrl));
                    return itemImage;
                })
                .toList();
        auctionItem.setUserId(userAuthentication.getUserId());
        auctionItem.setItemImages(itemImages);
        auctionItem.setStatus(AuctionStatus.ACTIVE);
        create(auctionItem);

        AuctionCreateEvent auctionCreateEvent = new AuctionCreateEvent();
        auctionCreateEvent.setAuctionId(auctionItem.getId());
        auctionCreateEvent.setEndTime(auctionItem.getEndTime());
        auctionCreateEvent.setEventType(AuctionEventType.CREATED);

        eventPublisher.publishEvent(auctionCreateEvent);
    }

    public void create(AuctionItem auctionItem) {
        auctionItemRepository.save(auctionItem);
    }

    public boolean checkAuctionActive(AuctionItem auctionItem) {
        return auctionItem.getStatus() == AuctionStatus.ACTIVE;
    }

    @Override
    public void updateStatus(AuctionCreateEvent auctionEvent) {
        AuctionItem auctionItem = auctionItemRepository.findById(auctionEvent.getAuctionId()).orElseThrow();
        auctionItem.setStatus(AuctionStatus.FINISHED);
        auctionItemRepository.save(auctionItem);
    }
}

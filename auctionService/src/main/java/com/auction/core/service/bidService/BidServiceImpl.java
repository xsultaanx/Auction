package com.auction.core.service.bidService;

import com.auction.core.service.auctionItem.AuctionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.auction.common.entity.AuctionItem;
import org.auction.common.entity.Bid;
import org.auction.common.repository.AuctionItemRepository;
import org.auction.common.repository.BidRepository;
import org.auction.common.service.user.UserAuthentication;
import org.springframework.stereotype.Service;
import com.auction.core.web.dto.request.BidDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final AuctionItemRepository auctionItemRepository;
    private final UserAuthentication userAuthentication;
    private final AuctionService auctionService;

    @Override
    public void create(Bid bid) {
        bidRepository.save(bid);
    }

    public Bid placeBid(BidDto bidDto) {
        AuctionItem item = auctionItemRepository.findById(UUID.fromString(bidDto.getAuctionItemId()))
                .orElseThrow(() -> new RuntimeException("Лот не найден"));
        if(!auctionService.checkAuctionActive(item)){
            throw new RuntimeException();
        }
        UUID user = userAuthentication.getUserId();

        validateNextBid(item.getMinStepPrice(), bidDto.getAmount());

        Bid bid = new Bid();
        bid.setTimestamp(LocalDateTime.now());
        bid.setAmount(bidDto.getAmount());
        bid.setAuctionItem(item);
        bid.setUserId(user);
        return bidRepository.save(bid);
    }

    @Override
    public List<Bid> getBidsForAuction(UUID auctionId) {
        return bidRepository.findByAuctionItem_Id(auctionId);
    }

    private void validateNextBid(BigDecimal step, BigDecimal amount) {
        if (step.compareTo(amount) > 0) {
            throw new RuntimeException("Сумма меньше допустимой");
        }
    }

    private BigDecimal getAcceptedSum(UUID id) {
        BigDecimal maxSum = bidRepository.findMaxBidAmountByAuctionItemId(id);
        return maxSum==null?BigDecimal.ZERO:maxSum;
    }


}

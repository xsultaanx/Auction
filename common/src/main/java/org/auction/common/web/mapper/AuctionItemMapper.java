package org.auction.common.web.mapper;

import org.auction.common.entity.AuctionItem;
import org.auction.common.web.dto.request.AuctionItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuctionItemMapper extends Mappable<AuctionItem, AuctionItemDto> {
}

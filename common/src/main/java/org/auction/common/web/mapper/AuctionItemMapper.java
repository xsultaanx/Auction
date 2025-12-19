package org.auction.common.web.mapper;

import org.auction.common.entity.AuctionItem;
import org.auction.common.utils.TimeMapper;
import org.auction.common.web.dto.request.AuctionItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TimeMapper.class)
public interface AuctionItemMapper extends Mappable<AuctionItem, AuctionItemDto> {
    @Override
    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "toLocalDateTime")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "toLocalDateTime")
    AuctionItem fromDto(AuctionItemDto dto);

    @Override
    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "toOffsetDateTime")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "toOffsetDateTime")
    AuctionItemDto toDto(AuctionItem entity);
}

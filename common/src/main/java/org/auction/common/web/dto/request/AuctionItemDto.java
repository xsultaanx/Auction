package org.auction.common.web.dto.request;

import lombok.*;
import org.auction.common.entity.Bid;
import org.auction.common.entity.ItemImage;
import org.auction.common.enums.AuctionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuctionItemDto {
    private String title; // Название лота
    private String description; // Описание
    private BigDecimal startingPrice; // Начальная цена
    private BigDecimal currentPrice; // Текущая ставка
    private AuctionStatus status; // Статус аукциона
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private BigDecimal minStepPrice; // Минимальный шаг ставки
    private UUID userId;

}

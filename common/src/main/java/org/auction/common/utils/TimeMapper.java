package org.auction.common.utils;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Component
public class TimeMapper {

    @Named("toLocalDateTime")
    public LocalDateTime toLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null
                ? offsetDateTime.plusHours(6).toLocalDateTime()
                : null;
    }

    @Named("toOffsetDateTime")
    public OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(OffsetDateTime.now().getOffset()) : null;
    }
}


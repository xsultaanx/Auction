package org.auction.job.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reminder {
    private Integer id;
    private LocalDateTime triggerTime;
    private String message;
}

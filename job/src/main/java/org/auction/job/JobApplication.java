package org.auction.job;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.auction.common.enums.AuctionEventType;
import org.auction.common.events.AuctionCreateEvent;
import org.auction.job.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class JobApplication {
	@Autowired
	Service service;
	public static void main(String[] args) {
		SpringApplication.run(JobApplication.class, args);
	}

//	@PostConstruct
//	@SneakyThrows
//	public void init() {
//		AuctionCreateEvent event = new AuctionCreateEvent();
//		event.setEventType(AuctionEventType.CREATED);
//		event.setAuctionId(UUID.randomUUID());
//		event.setEndTime(LocalDateTime.now().plusMinutes(1));
//		service.scheduleAuctionEnd(event);
//	}
}

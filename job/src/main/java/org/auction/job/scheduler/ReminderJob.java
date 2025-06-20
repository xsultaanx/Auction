package org.auction.job.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.auction.common.enums.AuctionEventType;
import org.auction.common.events.AuctionCreateEvent;
import org.auction.job.kafka.KafkaSender;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@Slf4j
public class ReminderJob extends QuartzJobBean {

    private final KafkaSender sender;
    @Value("${spring.kafka.topics.produce.quartz}")
    private String quartzTopic;

    public ReminderJob(KafkaSender sender) {
        this.sender = sender;
    }

    @Override
    @SneakyThrows
    protected void executeInternal(JobExecutionContext context) {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        ObjectMapper objectMapper = new ObjectMapper();

        UUID reminderId = UUID.fromString(dataMap.getString("reminderId"));
        String message = dataMap.getString("reminderMessage");
        log.info("Reminder AuctionID: {}, and: {}", reminderId, message);

        AuctionCreateEvent auctionCreateEvent = new AuctionCreateEvent();
        auctionCreateEvent.setAuctionId(reminderId);
        auctionCreateEvent.setEventType(AuctionEventType.FINISHED);
        String json = objectMapper.writeValueAsString(auctionCreateEvent);

        sender.send(quartzTopic, json);
        log.info("Send reminder event: {}", json);

        System.out.println("⏰ Напоминание сработало!");
        System.out.println("🕒 Время: " + Instant.now());
        System.out.println("🆔 ID: " + reminderId);
        System.out.println("📩 Сообщение: " + message);
    }
}

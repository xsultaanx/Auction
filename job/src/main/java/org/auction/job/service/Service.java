package org.auction.job.service;

import lombok.RequiredArgsConstructor;
import org.auction.common.events.AuctionCreateEvent;
import org.auction.job.scheduler.ReminderJob;
import org.quartz.*;

import java.sql.Timestamp;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    private final Scheduler scheduler;

    public void scheduleAuctionEnd(AuctionCreateEvent event) throws SchedulerException {

        System.out.println(event);
        JobDetail jobDetail = JobBuilder.newJob(ReminderJob.class)
                .withIdentity("reminderJob_" + event.getAuctionId(), "reminders")
                .usingJobData("reminderId", event.getAuctionId().toString())
                .usingJobData("reminderMessage", event.getEventType().name())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("reminderTrigger_" + event.getAuctionId(), "reminders")
                .startAt(Timestamp.valueOf(event.getEndTime()))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

    }
}

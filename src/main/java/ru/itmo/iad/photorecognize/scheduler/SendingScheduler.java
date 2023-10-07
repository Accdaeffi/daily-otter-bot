package ru.itmo.iad.photorecognize.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itmo.iad.photorecognize.service.ScheduledService;

@Component
@EnableScheduling
public class SendingScheduler {

    @Autowired
    ScheduledService scheduledService;

    @Scheduled(cron = "0 0 0 * * *")
    public void sendOtterToAll() {
        scheduledService.sendOtterToAll();
    }
}

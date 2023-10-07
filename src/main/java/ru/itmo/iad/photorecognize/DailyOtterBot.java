package ru.itmo.iad.photorecognize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.itmo.iad.photorecognize.service.ScheduledService;

@SpringBootApplication
public class DailyOtterBot {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(DailyOtterBot.class, args);

        applicationContext.getBean(ScheduledService.class).sendOtterToAll();
    }

}

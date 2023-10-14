package ru.itmo.iad.photorecognize.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CheckingConfig {

    @Value("${CHECKER_ID_SEPARATED_BY_COMMA}")
    private String listOfCheckers;

    @Bean("checkers")
    public List<String> checkers() {
        return List.of(listOfCheckers.split(","));
    }
}

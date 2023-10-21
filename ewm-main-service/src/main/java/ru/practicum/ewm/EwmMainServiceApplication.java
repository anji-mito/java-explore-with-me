package ru.practicum.ewm;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class EwmMainServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EwmMainServiceApplication.class, args);
    }

    @Configuration
    public static class AppConfig {
        @Bean
        public ModelMapper modelMapper() {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            return modelMapper;
        }
    }

}

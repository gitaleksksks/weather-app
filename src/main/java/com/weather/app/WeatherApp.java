package com.weather.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class WeatherApp {

    private static final Logger logger = LoggerFactory.getLogger(WeatherApp.class);

	public static void main(String[] args) {
        logger.info("Приложение запускается");
        SpringApplication.run(WeatherApp.class, args);
        logger.info("Приложение успешно запущено");
	}

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

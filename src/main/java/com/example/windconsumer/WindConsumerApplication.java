package com.example.windconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@Slf4j
public class WindConsumerApplication {

    public static void main(String[] args) {
        log.info("=== Starting WindConsumer Application ===");
        log.info("Kafka Topic: AWC_Wind_detect");
        log.info("WebSocket Port: 8084");
        log.info("WebSocket Endpoint: ws://localhost:8084/ws-wind");
        
        SpringApplication.run(WindConsumerApplication.class, args);
        
        log.info("=== WindConsumer Application Started Successfully ===");
    }
}

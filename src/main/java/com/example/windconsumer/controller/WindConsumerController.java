package com.example.windconsumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class WindConsumerController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "WindConsumer");
        response.put("port", 8084);
        response.put("topic", "AWC_Wind_detect");
        response.put("websocket", "/ws-wind");
        response.put("timestamp", LocalDateTime.now());
        
        log.info("Health check requested");
        return response;
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Wind Alert Consumer");
        response.put("description", "Consumes wind alert messages from Kafka and broadcasts via WebSocket");
        response.put("kafka", Map.of(
            "topic", "AWC_Wind_detect",
            "groupId", "wind-consumer-group"
        ));
        response.put("websocket", Map.of(
            "endpoint", "/ws-wind",
            "topic", "/topic/wind-alerts"
        ));
        
        return response;
    }
}

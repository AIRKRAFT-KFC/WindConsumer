package com.example.windconsumer.service;

import com.example.windconsumer.model.WindAlert;
import com.example.windconsumer.websocket.WebSocketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WindAlertConsumerService {
    
    private final ObjectMapper objectMapper;
    private final WebSocketService webSocketService;
    
    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        try {
            log.info("=== Wind Alert Message Received ===");
            log.info("Raw message: {}", message);
            System.out.println("Raw message: " + message);
            // JSON을 WindAlert 객체로 파싱
            WindAlert windAlert = objectMapper.readValue(message, WindAlert.class);
            
            log.info("🌪️  Wind Alert Details:");
            log.info("Alert Type: {}", windAlert.getAlertType());
            log.info("Risk Level: {}", windAlert.getRiskLevel());
            log.info("Aircraft: {} ({})", windAlert.getAircraft().getCallSign(), windAlert.getAircraft().getAircraftType());
            log.info("Route: {} → {}", windAlert.getAircraft().getDepartureAirport(), windAlert.getAircraft().getDestinationAirport());
            log.info("Current Altitude: {} ft (Target: {} ft)", windAlert.getAircraft().getAltitude(), windAlert.getAircraft().getAssignedAltitude());
            log.info("Wind Speed: {} {} at {}", windAlert.getWeather().getWindSpeed(), windAlert.getWeather().getWindSpeedUnit(), windAlert.getWeather().getNearestStationName());
            log.info("Alert Time: {}", windAlert.getAlertTime());
            log.info("Message Sequence: {}", windAlert.getMessageSequence());
            
            // WebSocket으로 실시간 전송
            webSocketService.sendWindAlert(windAlert);
            
            // 위험 수준에 따른 추가 처리
            handleRiskLevel(windAlert);
            
            log.info("✅ Wind alert processed successfully");
            
        } catch (Exception e) {
            log.error("❌ Error processing wind alert message: {}", e.getMessage(), e);
        }
    }
    
    private void handleRiskLevel(WindAlert windAlert) {
        switch (windAlert.getRiskLevel().toUpperCase()) {
            case "HIGH":
                log.warn("🚨 HIGH RISK wind alert for aircraft {}", windAlert.getAircraft().getCallSign());
                // 추가 알림 로직 구현 가능
                break;
            case "MODERATE":
                log.warn("⚠️  MODERATE risk wind alert for aircraft {}", windAlert.getAircraft().getCallSign());
                break;
            case "LOW":
                log.info("ℹ️  LOW risk wind alert for aircraft {}", windAlert.getAircraft().getCallSign());
                break;
            default:
                log.info("📊 Wind alert with risk level: {}", windAlert.getRiskLevel());
                break;
        }
    }
}

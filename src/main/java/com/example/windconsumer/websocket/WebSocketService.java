package com.example.windconsumer.websocket;

import com.example.windconsumer.model.WindAlert;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;
    
    /**
     * 풍속 경보를 WebSocket으로 전송
     */
    public void sendWindAlert(WindAlert windAlert) {
        try {
            log.info("🌐 Sending wind alert via WebSocket to /topic/wind-alerts");

            // JSON 문자열로 변환하여 전송
            String alertJson = objectMapper.writeValueAsString(windAlert);
            messagingTemplate.convertAndSend("/topic/wind-alerts", alertJson);

            log.info("✅ Wind alert sent successfully to WebSocket clients");

        } catch (Exception e) {
            log.error("❌ Failed to send wind alert via WebSocket: {}", e.getMessage(), e);
        }
    }
}

package com.hbm.mandis.hbm_backend.adapters.out.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbm.mandis.hbm_backend.adapters.in.web.controllers.dto.EcgMeasurementRequest;
import com.hbm.mandis.hbm_backend.adapters.in.web.controllers.dto.EcgMeasurementResponse;
import com.hbm.mandis.hbm_backend.adapters.out.mapper.MeasurementMapper;
import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;
import com.hbm.mandis.hbm_backend.core.port.in.measurement.SaveMeasurementInputPort;
import com.hbm.mandis.hbm_backend.core.port.out.websocket.WsOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler implements WsOutputPort {

    private static final Logger logger = LoggerFactory.getLogger(CustomWebSocketHandler.class);
    private final SaveMeasurementInputPort saveMeasurementInputPort;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public CustomWebSocketHandler(SaveMeasurementInputPort saveMeasurementInputPort) {
        this.saveMeasurementInputPort = saveMeasurementInputPort;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        logger.info("Frontend WebSocket session connected: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            logger.info("Received measurement via WebSocket: {}", message.getPayload());

            EcgMeasurementRequest measurementRequest = objectMapper.readValue(message.getPayload(), EcgMeasurementRequest.class);
            EcgMeasurementModel measurement = MeasurementMapper.requestToModel(measurementRequest);

            EcgMeasurementModel saved = saveMeasurementInputPort.save(measurement);
            sendMessage(saved);

        } catch (Exception e) {
            logger.error("Failed to process WebSocket message: {}", e.getMessage());
        }
    }

    @Override
    public void sendMessage(EcgMeasurementModel message) {
        for (WebSocketSession session : sessions) {
            try {
                EcgMeasurementResponse response = MeasurementMapper.modelToResponse(message);
                String jsonMessage = objectMapper.writeValueAsString(response);
                session.sendMessage(new TextMessage(jsonMessage));
            } catch (Exception e) {
                logger.error("Failed to send normal measurement to frontend: {}", e.getMessage());
            }
        }
    }

}

package com.hbm.mandis.hbm_backend.adapters.out.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbm.mandis.hbm_backend.core.port.out.websocket.WsOutputPort;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler implements WsOutputPort {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendMessage(String message) {

    }
}

package org.example.spring_demo_eduard_v2.handler;

import lombok.Generated;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class ProductCreationWsHandler extends TextWebSocketHandler {
    @Getter
    private final List<WebSocketSession> sessions = new LinkedList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("New connection established for session {}", session.getId());
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received message from session {}, text: {}", session.getId(), message.getPayload());
        session.sendMessage(new TextMessage("bla bla bla"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Connection closed for session {}", session.getId());
        sessions.remove(session);
    }
}

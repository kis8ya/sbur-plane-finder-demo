package com.github.kis8ya.sburplanefinderdemo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();

    @NonNull
    private final AircraftRepository repository;

    public List<WebSocketSession> getSessions() {
        return sessions;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Connection established from " + session.toString() + " at " + Instant.now().toString());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            System.out.println("Message received: '" + message + "', from " + session.toString());

            for (WebSocketSession openedSession : sessions) {
                if (session != openedSession) {
                    openedSession.sendMessage(message);
                    System.out.println("--> Sending message '" + message + "', to " + session.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Got an error: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed by " + session.toString() + " at " + Instant.now().toString());
    }
}

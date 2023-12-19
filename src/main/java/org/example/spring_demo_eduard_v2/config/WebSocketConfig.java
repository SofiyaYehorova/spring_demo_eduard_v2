package org.example.spring_demo_eduard_v2.config;

import lombok.RequiredArgsConstructor;
import org.example.spring_demo_eduard_v2.handler.ProductCreationWsHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ProductCreationWsHandler productCreationWsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(productCreationWsHandler, "/ws-products")
                .setAllowedOrigins("*");
    }
}

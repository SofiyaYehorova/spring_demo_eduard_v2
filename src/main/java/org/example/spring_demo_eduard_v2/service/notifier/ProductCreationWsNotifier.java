package org.example.spring_demo_eduard_v2.service.notifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.spring_demo_eduard_v2.dto.ProductDto;
import org.example.spring_demo_eduard_v2.handler.ProductCreationWsHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Component("wsNotifier")
@RequiredArgsConstructor
public class ProductCreationWsNotifier implements ProductCreationNotifier {

    private final ProductCreationWsHandler productCreationWsHandler;
    private final ObjectMapper objectMapper;

    @Override
    public void notify(ProductDto createdProduct) {
        productCreationWsHandler.getSessions().forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(createdProduct)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

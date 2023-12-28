package org.example.spring_demo_eduard_v2.services.notifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring_demo_eduard_v2.dto.ProductDto;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductCreationMailNotifier implements ProductCreationNotifier {
    private final MailSender mailSender;
    @Override
    public void notify(ProductDto createdProduct) {
            log.info("Sending mail notification about product creation");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("yehorova66@gmail.com");
            message.setTo("yehorova66@gmail.com");
            message.setSubject("New product created");
            message.setText("A new product was created with name: %s, price: %f, totalCount: %s".formatted(createdProduct.getName(), createdProduct.getPrice(), createdProduct.getTotalCount()));
            mailSender.send(message);
    }
}



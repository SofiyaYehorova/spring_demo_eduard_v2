package org.example.spring_demo_eduard_v2.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class Review {
    private String author;
    private String message;
    private LocalDateTime timestamp;

}

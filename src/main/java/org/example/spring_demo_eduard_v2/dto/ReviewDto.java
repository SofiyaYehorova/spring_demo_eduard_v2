package org.example.spring_demo_eduard_v2.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class ReviewDto {
    private String author;
    private String message;
    private LocalDateTime timestamp;
}

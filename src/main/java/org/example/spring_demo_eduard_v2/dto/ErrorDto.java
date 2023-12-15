package org.example.spring_demo_eduard_v2.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorDto {

    private int statusCode;
    private List<String> messages;
}

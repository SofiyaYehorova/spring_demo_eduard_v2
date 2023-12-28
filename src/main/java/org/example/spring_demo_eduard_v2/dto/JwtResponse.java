package org.example.spring_demo_eduard_v2.dto;

import lombok.Data;

@Data
public class JwtResponse {
    private final String accessToken;
    private final String refreshToken;
}

package org.example.spring_demo_eduard_v2.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}

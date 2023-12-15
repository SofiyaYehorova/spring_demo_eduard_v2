package org.example.spring_demo_eduard_v2.service;

import lombok.RequiredArgsConstructor;
import org.example.spring_demo_eduard_v2.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}

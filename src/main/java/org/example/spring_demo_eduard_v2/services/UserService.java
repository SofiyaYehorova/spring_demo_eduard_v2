package org.example.spring_demo_eduard_v2.services;

import lombok.RequiredArgsConstructor;
import org.example.spring_demo_eduard_v2.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CustomerRepository customerRepository;
}

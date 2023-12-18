package org.example.spring_demo_eduard_v2.service;

import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Service
public class DataStorageService {
    private final Deque<String> deque = new LinkedList<>();

    public void pushData(String data) {
        deque.push(data);
    }
    public Optional<String> pollData(){
        return Optional.ofNullable(deque.poll());
    }
}

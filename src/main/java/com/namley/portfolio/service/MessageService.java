package com.namley.portfolio.service;

import com.namley.portfolio.model.Message;
import com.namley.portfolio.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}

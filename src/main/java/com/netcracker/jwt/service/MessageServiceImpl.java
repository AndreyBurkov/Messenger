package com.netcracker.jwt.service;

import com.netcracker.jwt.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netcracker.jwt.domain.Message;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> getCorrespondence(Long toUser, Long fromUser, Long lastUserId) {
        return messageRepository.getCorrespondence(toUser, fromUser, lastUserId);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findOne(id);
    }

    @Override
    public boolean addMessage(Message message) {
        Message m = messageRepository.save(message);
        return m != null;
    }

    @Override
    public boolean updateMessage(Message message) {
        Message m = messageRepository.save(message);
        return m != null;
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepository.delete(id);
    }


}

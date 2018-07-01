package com.netcracker.jwt.service;


import com.netcracker.jwt.domain.Message;

import java.util.List;

public interface MessageService {

    List<Message> getAllMessages();
    List<Message> getCorrespondence(Long toUser, Long fromUser, Long lastUserId);
    Message getMessageById(Long id);
    boolean addMessage(Message message);
    boolean updateMessage(Message message);
    void deleteMessage(Long id);
}

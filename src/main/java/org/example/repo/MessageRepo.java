package org.example.repo;

import org.example.entity.Chat;
import org.example.entity.Friend;
import org.example.entity.Message;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MessageRepo extends JpaRepository<Message,Integer> {
    Message findByChatAndMessageTextAndSentDate(Chat chat, String messageText, Date sentDate);
    List<Message> findByChat_Id(int id);
}

package org.example.repo;

import org.example.entity.User;
import org.example.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepo extends JpaRepository<Chat,Integer> {
    Chat findByUserAndOpponentUser(User user, User user1);
    List<Chat> findByUserOrOpponentUser(User user, User opponentUser);
}

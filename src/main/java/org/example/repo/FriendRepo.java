package org.example.repo;

import org.example.entity.Friend;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRepo extends JpaRepository<Friend,Integer> {
    List<Friend> findByUser(User user);
    List<Friend> findByUserAndFriend_FullNameContaining(User user, String fullName);
}

package org.example.repo;

import org.example.entity.FriendRequest;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepo extends JpaRepository<FriendRequest,Integer> {
     List<FriendRequest> findByRequestSenderId(User requestSenderId);
     FriendRequest findByUserAndRequestSenderId(User user, User requestSenderId);
}

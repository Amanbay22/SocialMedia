package org.example.repo;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    List<User> findByFullNameContainingAndUsernameIsNot(String fullName, String userName);
}

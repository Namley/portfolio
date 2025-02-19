package com.namley.portfolio.repository;

import com.namley.portfolio.model.Post;
import com.namley.portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;

public interface UserRepository extends JpaRepository<User, Long> {
    FileChannel findByEmail(String email);
}

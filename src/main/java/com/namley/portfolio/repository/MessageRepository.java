package com.namley.portfolio.repository;

import com.namley.portfolio.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

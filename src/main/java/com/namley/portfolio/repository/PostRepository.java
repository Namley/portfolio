package com.namley.portfolio.repository;

import com.namley.portfolio.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
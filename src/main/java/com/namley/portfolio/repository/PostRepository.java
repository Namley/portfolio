package com.namley.portfolio.repository;

import com.namley.portfolio.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
    Post findById(long id);
}
package com.financescript.springapp.repositories;

import com.financescript.springapp.domains.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}

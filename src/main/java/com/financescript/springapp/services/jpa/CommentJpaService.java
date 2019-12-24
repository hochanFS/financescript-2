package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.repositories.CommentRepository;
import com.financescript.springapp.services.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentJpaService implements CommentService {
    private final CommentRepository commentRepository;

    public CommentJpaService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}

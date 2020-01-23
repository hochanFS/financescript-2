package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.repositories.CommentRepository;
import com.financescript.springapp.services.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@Service
public class CommentJpaService implements CommentService {
    private final CommentRepository commentRepository;

    public CommentJpaService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment findById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElse(null);
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    @PreAuthorize("#principal != null and (hasRole('ROLE_ADMIN') or #authorName.equals(#principal.name))")
    public void secureDelete(Comment comment, String authorName, Principal principal) {
        commentRepository.delete(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}

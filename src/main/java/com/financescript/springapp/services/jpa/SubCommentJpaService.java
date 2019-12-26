package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.SubComment;
import com.financescript.springapp.repositories.SubCommentRepository;
import com.financescript.springapp.services.SubCommentService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SubCommentJpaService implements SubCommentService {

    private final SubCommentRepository subCommentRepository;

    public SubCommentJpaService(SubCommentRepository subCommentRepository) {
        this.subCommentRepository = subCommentRepository;
    }

    @Override
    public void deleteById(Long id) {
        subCommentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(SubComment object) {
        subCommentRepository.delete(object);
    }

    @Override
    @Transactional
    public void save(SubComment object) {
        subCommentRepository.save(object);
    }
}

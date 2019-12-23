package com.financescript.springapp.services;

import com.financescript.springapp.domains.SubComment;

public interface SubCommentService {
    void deleteById(Long id);

    void delete(SubComment object);

    void save(SubComment object);
}

package com.financescript.springapp.repositories;

import com.financescript.springapp.domains.SubComment;
import org.springframework.data.repository.CrudRepository;

public interface SubCommentRepository extends CrudRepository<SubComment, Long> {
}

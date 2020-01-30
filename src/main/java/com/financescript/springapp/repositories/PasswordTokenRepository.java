package com.financescript.springapp.repositories;

import com.financescript.springapp.domains.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordTokenRepository extends CrudRepository<PasswordResetToken, Long> {

}

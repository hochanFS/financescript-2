package com.financescript.springapp.repositories;

import com.financescript.springapp.domains.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}

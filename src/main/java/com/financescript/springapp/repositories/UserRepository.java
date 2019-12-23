package com.financescript.springapp.repositories;

import com.financescript.springapp.domains.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

}

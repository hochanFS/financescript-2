package com.financescript.springapp.services;

import com.financescript.springapp.domains.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void delete(User object);
    User save(User user);
    void deleteById(Long id);
}

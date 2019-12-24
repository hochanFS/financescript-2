package com.financescript.springapp.services;

import com.financescript.springapp.domains.User;

public interface UserService {
    void delete(User object);
    User save(User user);
    void deleteById(Long id);
}

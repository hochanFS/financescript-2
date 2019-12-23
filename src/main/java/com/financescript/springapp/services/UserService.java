package com.financescript.springapp.services;

import com.financescript.springapp.domains.User;

public interface UserService {
    void delete(User object);
    void save(User user);
}

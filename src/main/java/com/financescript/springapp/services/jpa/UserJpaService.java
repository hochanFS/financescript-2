package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.User;
import com.financescript.springapp.repositories.UserRepository;
import com.financescript.springapp.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserJpaService implements UserService {
    private final UserRepository userRepository;

    public UserJpaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void delete(User object) {
        userRepository.delete(object);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

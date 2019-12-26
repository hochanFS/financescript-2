package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.domains.SubComment;
import com.financescript.springapp.domains.User;
import com.financescript.springapp.repositories.ArticleRepository;
import com.financescript.springapp.repositories.CommentRepository;
import com.financescript.springapp.repositories.SubCommentRepository;
import com.financescript.springapp.repositories.UserRepository;
import com.financescript.springapp.services.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserJpaService implements UserService {
    private final UserRepository userRepository;

    public UserJpaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    @Transactional
    public void delete(User object) {
        userRepository.delete(object);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

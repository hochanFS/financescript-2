package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.SubComment;
import com.financescript.springapp.domains.User;
import com.financescript.springapp.repositories.ArticleRepository;
import com.financescript.springapp.repositories.CommentRepository;
import com.financescript.springapp.repositories.SubCommentRepository;
import com.financescript.springapp.repositories.UserRepository;
import com.financescript.springapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserJpaServiceTest {

    @Mock
    UserRepository userRepository;

    UserService userService;

    private final Long USER1_ID = 1L;
    private User user1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        List<User> users = new ArrayList<>();
        userService = new UserJpaService(userRepository);
        user1 = new User();
        user1.setId(USER1_ID);
        users.add(user1);
        when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    void save() {
        User user2 = new User();
        user2.setId(2L);
        userService.save(user2);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void delete() {
        userService.delete(user1);
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    void deleteById() {
        userService.deleteById(USER1_ID);
        verify(userRepository, times(1)).deleteById(anyLong());
    }
}
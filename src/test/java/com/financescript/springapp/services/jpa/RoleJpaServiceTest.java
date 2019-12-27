package com.financescript.springapp.services.jpa;

import com.financescript.springapp.repositories.RoleRepository;
import com.financescript.springapp.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RoleJpaServiceTest {

    @Mock
    RoleRepository roleRepository;

    RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        roleService = new RoleJpaService(roleRepository);
    }

    @Test
    void findRoleByName() {

        roleService.findRoleByName("MEMBER");
        verify(roleRepository, times(1)).findRoleByName(anyString());
    }


}
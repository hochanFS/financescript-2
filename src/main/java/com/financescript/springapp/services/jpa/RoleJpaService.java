package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Role;
import com.financescript.springapp.repositories.RoleRepository;
import com.financescript.springapp.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleJpaService implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleJpaService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(String roleName) {
        return this.roleRepository.findRoleByName(roleName);
    }
}

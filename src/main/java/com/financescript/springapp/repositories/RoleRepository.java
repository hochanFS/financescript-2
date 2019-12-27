package com.financescript.springapp.repositories;

import com.financescript.springapp.domains.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(String theRoleName);
}

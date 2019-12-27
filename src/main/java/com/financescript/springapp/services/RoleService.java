package com.financescript.springapp.services;

import com.financescript.springapp.domains.Role;

public interface RoleService {
    public Role findRoleByName(String theRoleName);
}

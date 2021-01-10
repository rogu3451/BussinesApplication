package pl.busman.project.service;

import pl.busman.project.model.Role;
import pl.busman.project.model.dto.UsersWithRoleQuery;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void addRole(Role role);

    Role getRole(Long id);

    void deleteRole(Long id);

    Role createRole(UsersWithRoleQuery usersWithRoleQuery);

    String getRoleByUsername(String username);
}

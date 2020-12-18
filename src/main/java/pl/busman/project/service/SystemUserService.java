package pl.busman.project.service;

import org.springframework.stereotype.Service;
import pl.busman.project.model.Project;
import pl.busman.project.model.SystemUser;
import pl.busman.project.model.dto.UsersWithRoleQuery;

import java.util.List;


public interface SystemUserService {

    List<UsersWithRoleQuery> getAllUsersWithRole();

    void addSystemUser(SystemUser systemUser);

    SystemUser getSystemUser(Long id);

    void deleteSystemUser(Long id);

    boolean checkIfUsernameExist(String username);

    UsersWithRoleQuery getAllUserWithRoleById(Long id);

}


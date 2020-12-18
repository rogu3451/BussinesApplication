package pl.busman.project.service;

import org.springframework.stereotype.Service;
import pl.busman.project.model.Project;
import pl.busman.project.model.SystemUser;

import java.util.List;


public interface SystemUserService {

    List<SystemUser> getAllSystemUsers();

    void addSystemUser(SystemUser systemUser);

    SystemUser getSystemUser(Long id);

    void deleteSystemUser(Long id);

    boolean checkIfUsernameExist(String username);

}


package pl.busman.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.busman.project.exception.ProjectException.ProjectError;
import pl.busman.project.exception.ProjectException.ProjectException;
import pl.busman.project.exception.SystemUserException.SystemUserError;
import pl.busman.project.exception.SystemUserException.SystemUserError;
import pl.busman.project.exception.SystemUserException.SystemUserException;
import pl.busman.project.model.Project;
import pl.busman.project.model.SystemUser;
import pl.busman.project.model.dto.UsersWithRoleQuery;
import pl.busman.project.repository.RoleRepository;
import pl.busman.project.repository.SystemUserRepository;

import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    SystemUserRepository systemUserRepository;

    @Autowired
    RoleRepository roleRepository;

    public List<UsersWithRoleQuery> getAllUsersWithRole() {
        return (List<UsersWithRoleQuery>)systemUserRepository.getAllUsersWithRole();
    }

    public void addSystemUser(SystemUser systemUser) {
        System.out.println("ID: " + systemUser.getId());
        if (systemUser.getId() != null) {
            updateSystemUser(systemUser);
        } else {
            systemUserRepository.save(systemUser);
        }
    }


    public void updateSystemUser(SystemUser systemUser){
        systemUserRepository.findById(systemUser.getId())
                .map(userFromDB -> {
                    userFromDB.setUsername(systemUser.getUsername());
                    try{
                        if(!systemUser.getPassword().isEmpty()){
                            userFromDB.setPassword(systemUser.getPassword());
                        }
                    }
                    catch (Exception e){
                    }
                    return systemUserRepository.save(userFromDB);
                }).orElseThrow(()-> new SystemUserException(SystemUserError.USER_NOT_FOUND));
    }


    public SystemUser getSystemUser(Long id) {
        return systemUserRepository.findById(id).orElseThrow(()-> new SystemUserException(SystemUserError.USER_NOT_FOUND));
    }

    public void deleteSystemUser(Long id) {
        SystemUser systemUser = systemUserRepository.findById(id).orElseThrow(()-> new SystemUserException(SystemUserError.USER_NOT_FOUND));
        if(systemUser!=null){
            systemUserRepository.delete(systemUser);
        }
    }


    public boolean checkIfUsernameExist(String username) {
        return systemUserRepository.existsByUsername(username);
    }

    public UsersWithRoleQuery getAllUserWithRoleById(Long id) {
        return systemUserRepository.getAllUserWithRoleById(id);
    }

    public SystemUser createSystemUser(UsersWithRoleQuery usersWithRoleQuery) {
        try{
            if(usersWithRoleQuery.getPassword().isEmpty()){
                SystemUser systemUser = new SystemUser(usersWithRoleQuery.getId(), usersWithRoleQuery.getUsername());
                return systemUser;
            }else{
                SystemUser systemUser = new SystemUser(usersWithRoleQuery.getId(), usersWithRoleQuery.getUsername(),usersWithRoleQuery.getPassword());
                return systemUser;
            }
        }catch (Exception e){
            System.out.println("User creation error!");
        }
        return null;
    }

}



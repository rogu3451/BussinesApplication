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
import pl.busman.project.repository.SystemUserRepository;

import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    SystemUserRepository systemUserRepository;

    public List<SystemUser> getAllSystemUsers() {
        return systemUserRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public void addSystemUser(SystemUser systemUser) {
        if(systemUser.getId()!=null){
            updateSystemUser(systemUser);
        }else{
            systemUserRepository.save(systemUser);
        }
    }

    public void updateSystemUser(SystemUser systemUser){
        systemUserRepository.findById(systemUser.getId())
                .map(userFromDB -> {
                    userFromDB.setUsername(systemUser.getUsername());
                    try{
                        userFromDB.setPassword(systemUser.getPassword());
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
}



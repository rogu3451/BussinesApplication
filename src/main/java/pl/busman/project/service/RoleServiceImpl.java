package pl.busman.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.busman.project.exception.ProjectException.ProjectError;
import pl.busman.project.exception.ProjectException.ProjectException;
import pl.busman.project.exception.SystemUserException.SystemUserError;
import pl.busman.project.exception.SystemUserException.SystemUserException;
import pl.busman.project.model.Project;
import pl.busman.project.model.Role;
import pl.busman.project.repository.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public void addRole(Role role) {
        if(role.getId()!=null){
            updateRole(role);
        }else{
            roleRepository.save(role);
        }
    }

    public void updateRole(Role role){
        roleRepository.findById(role.getId())
                .map(roleFromDb -> {
                    roleFromDb.setRole(role.getRole());
                    roleFromDb.setUsername(role.getUsername());
                    return roleRepository.save(roleFromDb);
                }).orElseThrow(()-> new SystemUserException(SystemUserError.ROLE_NOT_FOUND));
    }


    public Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow(()-> new SystemUserException(SystemUserError.ROLE_NOT_FOUND));
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(()-> new SystemUserException(SystemUserError.ROLE_NOT_FOUND));
        if(role!=null){
            roleRepository.delete(role);
        }
    }
}

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

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    SystemUserRepository systemUserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    public List<UsersWithRoleQuery> getAllUsersWithRole() {
        return (List<UsersWithRoleQuery>)systemUserRepository.getAllUsersWithRole();
    }

    public void addSystemUser(SystemUser systemUser) {
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
            roleService.deleteRole(roleRepository.getIdByUsername(systemUser.getUsername()));
        }
    }


    public boolean checkIfUsernameExist(String username) {
        return systemUserRepository.existsByUsername(username);
    }

    public UsersWithRoleQuery getAllUsersWithRoleById(Long id) {
        return systemUserRepository.getAllUsersWithRoleById(id);
    }

    @Transactional
    public void updateSystemUser(UsersWithRoleQuery usersWithRoleQuery) {
        try{
            Long userId = usersWithRoleQuery.getUserId();
            String username = usersWithRoleQuery.getUsername();
            String firstName = usersWithRoleQuery.getFirstName();
            String lastName = usersWithRoleQuery.getLastName();
            String role = usersWithRoleQuery.getRole();
            Long roleId = usersWithRoleQuery.getRoleId();

            if(usersWithRoleQuery.getPassword().isEmpty()){  // Update without password because password is empty
                systemUserRepository.updateSystemUserWithoutPassword(userId, username,firstName,lastName);
            }else{// Update with password because password is provided
                String password = usersWithRoleQuery.getPassword();
                String encodedPassword = SystemUser.encodePassword(password);
                systemUserRepository.updateSystemUserWithPassword(userId,username,encodedPassword,firstName,lastName);
            }
                roleRepository.updateRoleById(username,role,roleId);  // update role

        }catch (Exception e){
            System.out.println("Something went wrong with updating user!");
        }
    }

    public List<SystemUser> getAllCustomers() {
        return (List<SystemUser>)systemUserRepository.getAllCustomers();
    }

    public String getUsernameById(Long id){
       return systemUserRepository.getUsernameById(id);
    }


    public SystemUser getCustomerById(Long customerId) {
        return systemUserRepository.getCustomerById(customerId);
    }

    public List<SystemUser> getCustomerDetailsByProjectId(Long projectId) {
        return systemUserRepository.getCustomerDetailsByProjectId(projectId);
    }


    public List<SystemUser> getAllEmployees() {
        return systemUserRepository.getAllEmployees();
    }

    @Override
    public SystemUser getEmployeeById(Long employeeId) {
        return systemUserRepository.getEmployeeById(employeeId);
    }

}



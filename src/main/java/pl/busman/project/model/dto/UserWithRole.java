package pl.busman.project.model.dto;

import org.apache.catalina.User;
import pl.busman.project.model.Role;
import pl.busman.project.model.SystemUser;

import javax.validation.Valid;


public class UserWithRole {

    private SystemUser systemUser;
    private Role role;

    public UserWithRole(SystemUser systemUser,Role role){
        this.systemUser = systemUser;
        this.role = role;
    }

    public UserWithRole(){
        SystemUser systemUser = new SystemUser();
        Role role = new Role();
        new UserWithRole(systemUser,role);
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}

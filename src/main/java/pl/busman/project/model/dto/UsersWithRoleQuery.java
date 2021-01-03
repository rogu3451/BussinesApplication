package pl.busman.project.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Data
@Getter
@Setter
public class UsersWithRoleQuery {

    private Long userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private Long roleId;

    public UsersWithRoleQuery(Long userId, String username, String firstName, String lastName, String role, Long roleId) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.roleId = roleId;
    }

    public UsersWithRoleQuery(){
    }

}

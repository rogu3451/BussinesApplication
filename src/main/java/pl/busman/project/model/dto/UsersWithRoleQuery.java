package pl.busman.project.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Data
@Getter
@Setter
public class UsersWithRoleQuery {

    private Long id;
    private String username;
    private String password;
    private String role;
    private Long role_id;

    public UsersWithRoleQuery(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }


    public UsersWithRoleQuery(Long id, String username, String role, Long role_id) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.role_id = role_id;

    }

    public UsersWithRoleQuery(){

    }


}

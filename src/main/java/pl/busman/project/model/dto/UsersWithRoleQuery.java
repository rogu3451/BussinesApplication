package pl.busman.project.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsersWithRoleQuery {

    private Long id;
    private String username;
    private String password;
    private String role;

    public UsersWithRoleQuery(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public UsersWithRoleQuery(Long id, String username,String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }


}

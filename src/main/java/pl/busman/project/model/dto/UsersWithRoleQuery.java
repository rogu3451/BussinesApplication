package pl.busman.project.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersWithRoleQuery {

    private Long id;
    private String username;
    private String role;

    public UsersWithRoleQuery(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}

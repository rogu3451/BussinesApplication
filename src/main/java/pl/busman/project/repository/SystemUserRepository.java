package pl.busman.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.busman.project.model.SystemUser;
import pl.busman.project.model.dto.UsersWithRoleQuery;

import java.util.Collection;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser,Long> {

    boolean existsByUsername(String username); // true if username exists in database


    @Query("SELECT new pl.busman.project.model.dto.UsersWithRoleQuery(user.id, user.username, role.role) FROM SystemUser user " +
                   "INNER JOIN Role role " +
                   "ON user.username = role.username " +
                   "ORDER BY user.id DESC")
    Collection<UsersWithRoleQuery> getAllUsersWithRole();


    @Query("SELECT new pl.busman.project.model.dto.UsersWithRoleQuery(user.id, user.username, role.role) FROM SystemUser user " +
            "INNER JOIN Role role " +
            "ON user.username = role.username " +
            "WHERE user.id = :idFromClient ")
            UsersWithRoleQuery getAllUserWithRoleById(@Param("idFromClient") Long id);

}

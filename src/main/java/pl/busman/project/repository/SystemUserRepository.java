package pl.busman.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.busman.project.model.SystemUser;
import pl.busman.project.model.dto.UsersWithRoleQuery;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser,Long> {

    boolean existsByUsername(String username); // true if username exists in database

    @Query("SELECT new pl.busman.project.model.SystemUser(user.id, user.username, user.firstName, user.lastName) FROM SystemUser user INNER JOIN" +
            " Role role ON user.username = role.username WHERE role.role = 'CUSTOMER' ORDER BY user.id")
    Collection<SystemUser> getAllCustomers();

    @Query("SELECT new pl.busman.project.model.dto.UsersWithRoleQuery(user.id, user.username, user.firstName, user.lastName, role.role, role.id) FROM SystemUser user " +
                   "INNER JOIN Role role " +
                   "ON user.username = role.username " +
                   "ORDER BY user.id DESC")
    Collection<UsersWithRoleQuery> getAllUsersWithRole();


    @Query("SELECT new pl.busman.project.model.dto.UsersWithRoleQuery(user.id, user.username, user.firstName, user.lastName, role.role, role.id ) FROM SystemUser user " +
            "INNER JOIN Role role " +
            "ON user.username = role.username " +
            "WHERE user.id = :idFromClient ")
    UsersWithRoleQuery getAllUsersWithRoleById(@Param("idFromClient") Long id);

    @Query("SELECT user.id FROM SystemUser user " +
            "WHERE user.username = :username ")
    Long getIdByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE SystemUser user SET user.username = :username, user.firstName = :firstName, user.lastName = :lastName  WHERE user.id = :userId")
    void updateSystemUserWithoutPassword(@Param("userId") Long userId, @Param("username") String username, @Param("firstName") String firstName, @Param("lastName") String lastName);


    @Transactional
    @Modifying
    @Query("UPDATE SystemUser user SET user.username = :username, user.firstName = :firstName, user.lastName = :lastName, user.password = :password  WHERE user.id = :userId")
    void updateSystemUserWithPassword(@Param("userId") Long userId, @Param("username") String username,  @Param("password") String password, @Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT user.username FROM SystemUser user " +
            " WHERE user.id = :userId")
    String getUsernameById(@Param("userId") Long id);
}

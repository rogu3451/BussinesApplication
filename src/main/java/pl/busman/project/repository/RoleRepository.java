package pl.busman.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.busman.project.model.Role;
import pl.busman.project.model.dto.UsersWithRoleQuery;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByUsername(String username); // true if username exists in database

    @Query("SELECT role.id FROM Role role " +
            "WHERE role.username = :usernameFromApp")
    Long getIdByUsername(@Param("usernameFromApp") String username);

}


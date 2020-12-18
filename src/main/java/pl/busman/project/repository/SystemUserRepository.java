package pl.busman.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.busman.project.model.SystemUser;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser,Long> {

    boolean existsByUsername(String username); // true if username exists in database

}

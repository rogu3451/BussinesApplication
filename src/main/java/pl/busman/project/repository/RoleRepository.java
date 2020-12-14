package pl.busman.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.busman.project.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

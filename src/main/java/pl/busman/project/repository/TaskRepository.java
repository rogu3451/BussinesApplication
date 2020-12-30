package pl.busman.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.busman.project.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


}

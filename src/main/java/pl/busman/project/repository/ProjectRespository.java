package pl.busman.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.busman.project.model.Project;
import pl.busman.project.model.Task;

import java.util.Collection;


@Repository
public interface ProjectRespository extends JpaRepository<Project, Long> {


    @Query("SELECT new pl.busman.project.model.Project(project.id, project.name, project.description,project.customerId) " +
            "FROM Project project WHERE project.id IN (SELECT task.project_id FROM Task task WHERE task.employee_id = :id)")
    Collection<Project> getAllProjectsByUserId(@Param("id") Long id);
}

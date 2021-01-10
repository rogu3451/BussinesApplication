package pl.busman.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.busman.project.model.Project;
import pl.busman.project.model.Task;
import pl.busman.project.model.dto.ProjectWithCustomerDetalis;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;


@Repository
public interface ProjectRespository extends JpaRepository<Project, Long> {


    @Query("SELECT new pl.busman.project.model.Project(project.id, project.name, project.description,project.customerId) " +
            "FROM Project project WHERE project.id IN (SELECT task.project_id FROM Task task WHERE task.employee_id = :id)")
    Collection<Project> getAllProjectsForEmployeeById(@Param("id") Long id);


    @Query("SELECT new pl.busman.project.model.dto.ProjectWithCustomerDetalis(project.id, project.name, project.description, systemUser.firstName, systemUser.lastName) " +
            "FROM Project project INNER JOIN SystemUser systemUser ON project.customerId = systemUser.id " +
            "WHERE systemUser.id IN (SELECT project.customerId FROM Project project) ORDER BY project.id DESC")
    List<ProjectWithCustomerDetalis> getAllProjectsWithCustomerDetails();


    @Transactional
    @Modifying
    @Query("UPDATE Project project SET project.name = :name, project.description = :description, project.customerId = :customerId  WHERE project.id = :projectId")
    void  updateProject(@Param("projectId") Long projectId, @Param("name") String name, @Param("description") String description, @Param("customerId") Long customerId);


    @Query("SELECT new pl.busman.project.model.Project(project.id, project.name, project.description,project.customerId) " +
            "FROM Project project WHERE project.customerId = :id")
    Collection<Project> getAllProjectsForCustomerById(@Param("id") Long id);

    @Query("SELECT project.name FROM Project project WHERE project.id = :id")
    String getProjectNameById(@Param("id") Long projectId);


    @Query("SELECT new pl.busman.project.model.Project(project.id, project.name, project.description,project.customerId) " +
            "FROM Project project WHERE project.customerId = :customerId AND project.id = :projectId ")
    List<Project> getProjectByCustomerIdAndProjectId( @Param("customerId") Long customerId,@Param("projectId") Long projectId);
}

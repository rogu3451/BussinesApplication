package pl.busman.project.repository;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.busman.project.model.Project;
import pl.busman.project.model.Task;

@Repository
public interface ReportRepository extends JpaRepository<Task, Long> {

    @Query("SELECT DISTINCT new pl.busman.project.model.Project( project.id , project.name, project.description,project.customerId) FROM Task task " +
            "INNER JOIN Project project ON task.project_id = project.id " +
            "WHERE task.employee_id = :employeeId AND task.status='DONE' AND task.dateOfEnd LIKE CONCAT(:date ,'%') ORDER BY project.id ASC")
    List<Project> getProjectsByEmployeeAndDateIdWhereExistsDoneTasks(@Param("employeeId") Long id,  @Param("date") String date);



    @Query("SELECT new pl.busman.project.model.Task(task.id, task.project_id, task.employee_id, task.title, task.description, task.status, task.neededTime, " +
            "task.dateOfCreation, task.cost, task.dateOfEnd) FROM Task task WHERE task.status='DONE' AND task.project_id = :projectId " +
            "AND task.dateOfEnd LIKE CONCAT(:date ,'%') ORDER BY task.dateOfEnd ASC")
    List<Task> getDoneProjectTasksByProjectIdAndDate(@Param("projectId") Long projectId, @Param("date") String date);

    @Query("SELECT SUM(task.neededTime) FROM Task task WHERE task.status='DONE' " +
            "AND task.employee_id = :employeeId AND task.dateOfEnd LIKE CONCAT(:date ,'%')")
    Double getTotalWorkHoursPerMonthAndYear(@Param("employeeId") Long employeeId, @Param("date") String date);



}

package pl.busman.project.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.busman.project.model.Task;
import pl.busman.project.model.dto.UsersWithRoleQuery;

import javax.transaction.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query("SELECT new pl.busman.project.model.Task(task.id, task.project_id, task.employee_id, task.title, task.description, task.status, task.neededTime," +
            "task.dateOfCreation, task.cost, task.dateOfEnd) FROM Task task " +
            "WHERE task.project_id = :id")
    Collection<Task> findAllByProject_id(@Param("id") Long id);

    @Query("SELECT new pl.busman.project.model.Task(task.id, task.project_id, task.employee_id, task.title, task.description, task.status, task.neededTime," +
            "task.dateOfCreation, task.cost, task.dateOfEnd) FROM Task task " +
            "WHERE task.employee_id = :employeeId AND task.project_id = :projectId")
    Collection<Task> findAllByEmployeeIdAndProjectId(@Param("employeeId") Long employeeId, @Param("projectId") Long projectId);

    @Transactional
    @Modifying
    @Query("UPDATE Task task SET task.status = :status, task.neededTime = :neededTime, task.cost = :cost, task.dateOfEnd = :dateOfEnd WHERE task.id = :id")
    void updateTask(@Param("status") String status, @Param("neededTime") Double neededTime, @Param("cost") Double cost, @Param("dateOfEnd") String dateOfEnd, @Param("id") Long id);
}

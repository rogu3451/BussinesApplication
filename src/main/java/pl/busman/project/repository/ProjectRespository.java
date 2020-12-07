package pl.busman.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.busman.project.model.Project;

import java.awt.*;

@Repository
public interface ProjectRespository extends JpaRepository<Project, Long> {

}

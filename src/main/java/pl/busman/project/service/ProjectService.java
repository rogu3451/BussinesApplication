package pl.busman.project.service;

import org.springframework.stereotype.Service;
import pl.busman.project.model.Project;

import java.util.List;


public interface ProjectService {

    List<Project> getAllProjects();

    void addProject(Project project);

    Project getProject(Long id);

    void deleteProject(Long id);

}

package pl.busman.project.exception.ProjectException;

public class ProjectException extends RuntimeException {

    private ProjectError projectError;

    public ProjectException(ProjectError projectError){
        this.projectError= projectError;
    }

    public ProjectError getProjectError() {
        return projectError;
    }


}

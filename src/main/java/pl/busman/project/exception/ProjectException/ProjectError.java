package pl.busman.project.exception.ProjectException;

public enum ProjectError {
    PROJECT_NOT_FOUND("Project does not exist");

    private String message;

    ProjectError(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

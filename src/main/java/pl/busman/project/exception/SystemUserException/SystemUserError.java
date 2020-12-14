package pl.busman.project.exception.SystemUserException;

public enum SystemUserError {
    USER_NOT_FOUND("User does not exist"),
    ROLE_NOT_FOUND("Role does not exist");

    private String message;

    SystemUserError(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

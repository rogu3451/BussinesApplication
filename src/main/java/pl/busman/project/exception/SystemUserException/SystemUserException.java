package pl.busman.project.exception.SystemUserException;

import pl.busman.project.exception.ProjectException.ProjectError;


public class SystemUserException extends RuntimeException {

    private SystemUserError systemUserError;

    public SystemUserException(SystemUserError systemUserError){
        this.systemUserError = systemUserError;
    }

    public SystemUserError getSystemUserError() {
        return systemUserError;
    }


}

package pl.busman.project.exception.ProjectException;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.busman.project.exception.ErrorInfo;


public class ProjectExceptionHandler {

    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ErrorInfo> handleException(ProjectException e){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if(ProjectError.PROJECT_NOT_FOUND.equals(e.getProjectError())){
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getProjectError().getMessage()));
    }
}

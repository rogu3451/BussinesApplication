package pl.busman.project.exception.SystemUserException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.busman.project.exception.ErrorInfo;

public class SystemUserExceptionHandler {

    @ExceptionHandler(SystemUserException.class)
    public ResponseEntity<ErrorInfo> handleException(SystemUserException e){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if(SystemUserError.USER_NOT_FOUND.equals(e.getSystemUserError())){
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getSystemUserError().getMessage()));
    }
}

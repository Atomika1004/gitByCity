package com.atomika.gitByCity.handler;
import com.atomika.gitByCity.dto.exp.ExceptionMessage;
import com.atomika.gitByCity.handler.exception.ExistsException;
import com.atomika.gitByCity.handler.exception.NotFoundException;
import com.atomika.gitByCity.handler.exception.NotUniqException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionMessage> notFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionMessage> mismatchException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(ExistsException.class)
    public ResponseEntity<ExceptionMessage> existsException(ExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(NotUniqException.class)
    public ResponseEntity<ExceptionMessage> notUniqException(NotUniqException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionMessage(exception.getMessage()));
    }

}

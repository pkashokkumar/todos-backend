package xyz.cloudease.todo.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            EmptyResultDataAccessException.class
    })
    @ResponseBody
    public ResponseEntity handleNotFound(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({
            ScheduleCreationFailed.class
    })
    @ResponseBody
    public ResponseEntity handleSchedulerException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
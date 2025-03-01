package nl.gerimedica.assignment.exception;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import nl.gerimedica.assignment.exception.model.ErrorDTO;
import nl.gerimedica.assignment.exception.subexceptions.AppointmentNotFoundException;
import nl.gerimedica.assignment.exception.subexceptions.PatientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDTO> handleException(BaseException ex) {
        log.error("[handleException::BaseException] Exception::{}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorDto(ex));
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(PatientNotFoundException ex) {
        log.error("[handleException::PatientNotFoundException] Exception::{}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorDto(ex));
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(AppointmentNotFoundException ex) {
        log.error("[handleException::AppointmentNotFoundException] Exception::{}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorDto(ex));
    }

    private ErrorDTO createErrorDto(BaseException ex) {
        return ErrorDTO.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .reason(ex.getReason())
                .date(ex.getDate())
                .build();
    }
}
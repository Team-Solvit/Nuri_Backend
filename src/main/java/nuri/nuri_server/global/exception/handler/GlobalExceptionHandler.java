package nuri.nuri_server.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.global.exception.ErrorResponse;
import nuri.nuri_server.global.exception.NuriBusinessException;
import nuri.nuri_server.global.exception.NuriSystemError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException authenticationException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(authenticationException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(accessDeniedException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(methodArgumentNotValidException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NuriBusinessException.class)
    public ResponseEntity<ErrorResponse> handleNuriBusinessException(NuriBusinessException nuriBusinessException) {
        loggingWarning(nuriBusinessException);
        ErrorResponse errorResponse = ErrorResponse.of(nuriBusinessException);
        return new ResponseEntity<>(errorResponse, nuriBusinessException.getStatus());
    }

    @ExceptionHandler(NuriSystemError.class)
    public ResponseEntity<ErrorResponse> handleNuriSystemError(NuriSystemError nuriSystemError) {
        loggingError(nuriSystemError);
        ErrorResponse errorResponse = ErrorResponse.of(nuriSystemError);
        return new ResponseEntity<>(errorResponse, nuriSystemError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        loggingError(exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void loggingError(Exception exception) {
        log.error("Exception occurred: [{}] - Message: [{}]", exception.getClass().getName(), exception.getMessage());
    }

    private void loggingWarning(Exception exception) {
        log.warn("Exception occurred: [{}] - Message: [{}]", exception.getClass().getName(), exception.getMessage());
    }
}

package nuri.nuri_server.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.global.exception.ErrorResponse;
import nuri.nuri_server.global.exception.NuriBusinessException;
import nuri.nuri_server.global.exception.NuriSystemError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                .message(httpRequestMethodNotSupportedException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(Objects.requireNonNull(methodArgumentNotValidException.getBindingResult().getFieldError()).getDefaultMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(illegalArgumentException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NuriBusinessException.class)
    public ResponseEntity<ErrorResponse> handleNuriBusinessException(NuriBusinessException nuriBusinessException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(nuriBusinessException.getStatus().getReasonPhrase())
                .message(nuriBusinessException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, nuriBusinessException.getStatus());
    }

    @ExceptionHandler(NuriSystemError.class)
    public ResponseEntity<ErrorResponse> handleNuriSystemError(NuriSystemError nuriSystemError) {
        loggingError(nuriSystemError);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(nuriSystemError.getStatus().getReasonPhrase())
                .message(nuriSystemError.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, nuriSystemError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        loggingError(exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void loggingError(Exception exception) {
        log.error("Exception occurred: [{}] - Message: [{}]", exception.getClass().getName(), exception.getMessage());
    }
}

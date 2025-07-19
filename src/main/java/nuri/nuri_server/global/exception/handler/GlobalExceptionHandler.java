package nuri.nuri_server.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.global.exception.ErrorResponse;
import nuri.nuri_server.global.exception.NuriBusinessException;
import nuri.nuri_server.global.exception.NuriSystemError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message("인증 오류가 발생했습니다.")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message("권한으로 인한 인증 오류가 발생했습니다.")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                .message(httpRequestMethodNotSupportedException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(Objects.requireNonNull(methodArgumentNotValidException.getBindingResult().getFieldError()).getDefaultMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("잘못된 인자 값 입니다.")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("요청 본문을 읽을 수 없습니다.")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NuriBusinessException.class)
    public ResponseEntity<ErrorResponse> handleNuriBusinessException(NuriBusinessException nuriBusinessException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(nuriBusinessException.getStatus().getReasonPhrase())
                .message(nuriBusinessException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, nuriBusinessException.getStatus());
    }

    @ExceptionHandler(NuriSystemError.class)
    public ResponseEntity<ErrorResponse> handleNuriSystemError(NuriSystemError nuriSystemError) {
        loggingError(nuriSystemError);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(nuriSystemError.getStatus().getReasonPhrase())
                .message(nuriSystemError.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, nuriSystemError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        loggingError(exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("서버에서 예상치 못한 오류가 발생했습니다.")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void loggingError(Exception exception) {
        log.error("예외 발생 : [{}] - 메세지 : [{}]", exception.getClass().getName(), exception.getMessage());
    }
}

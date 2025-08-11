package nuri.nuri_server.global.exception.handler;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.global.exception.ErrorResponse;
import nuri.nuri_server.global.exception.ErrorType;
import nuri.nuri_server.global.exception.NuriBusinessException;
import nuri.nuri_server.global.exception.NuriSystemError;
import nuri.nuri_server.global.security.exception.SecurityAccessDeniedException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @GraphQlExceptionHandler(AuthenticationException.class)
    public GraphQLError handleAuthenticationException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message("인증 오류가 발생했습니다.")
                .errorType(ErrorType.UNAUTHENTICATED)
                .extensions(errorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(SecurityAccessDeniedException.class)
    public GraphQLError handleGraphQLAccessDeniedException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message("권한으로 인한 인증 오류가 발생했습니다.")
                .errorType(ErrorType.FORBIDDEN)
                .extensions(errorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(AccessDeniedException.class)
    public GraphQLError handleAccessDeniedException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message("권한으로 인한 인증 오류가 발생했습니다.")
                .errorType(ErrorType.FORBIDDEN)
                .extensions(errorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(ConstraintViolationException.class)
    public GraphQLError handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message(constraintViolationException.getConstraintViolations().stream().findFirst()
                        .map(ConstraintViolation::getMessage)
                        .orElse("입력값이 유효하지 않습니다."))
                .errorType(ErrorType.VALIDATION_ERROR)
                .extensions(errorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(MethodArgumentNotValidException.class)
    public GraphQLError handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message(methodArgumentNotValidException.getBindingResult().getFieldError() != null
                                                ? methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage()
                                                : "Validation error occurred")
                .errorType(ErrorType.VALIDATION_ERROR)
                .extensions(errorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(IllegalArgumentException.class)
    public GraphQLError handleIllegalArgumentException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message("잘못된 인자 값 입니다.")
                .errorType(ErrorType.VALIDATION_ERROR)
                .extensions(errorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(HttpMessageNotReadableException.class)
    public GraphQLError handleHttpMessageNotReadableException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message("요청 본문을 읽을 수 없습니다.")
                .errorType(ErrorType.DATA_FETCHING_EXCEPTION)
                .extensions(errorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(NuriBusinessException.class)
    public GraphQLError handleNuriBusinessException(NuriBusinessException nuriBusinessException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(nuriBusinessException.getStatus().getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message(nuriBusinessException.getMessage())
                .errorType(nuriBusinessException.getErrorType())
                .extensions(errorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(NuriSystemError.class)
    public GraphQLError handleNuriSystemError(NuriSystemError nuriSystemError) {
        loggingError(nuriSystemError);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(nuriSystemError.getStatus().getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message(nuriSystemError.getMessage())
                .errorType(ErrorType.INTERNAL_SERVER_ERROR)
                .extensions(errorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(Exception.class)
    public GraphQLError handleException(Exception exception) {
        loggingError(exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message("서버에서 예상치 못한 오류가 발생했습니다.")
                .errorType(ErrorType.INTERNAL_SERVER_ERROR)
                .extensions(errorResponse.getMap())
                .build();
    }

    private void loggingError(Exception exception) {
        log.error("예외 발생 : [{}] - 메세지 : [{}]", exception.getClass().getName(), exception.getMessage());
    }
}

package nuri.nuri_server.global.exception.graphql.handler;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.global.exception.graphql.GraphQLErrorResponse;
import nuri.nuri_server.global.exception.graphql.GraphQLErrorType;
import nuri.nuri_server.global.exception.NuriBusinessException;
import nuri.nuri_server.global.exception.NuriSystemError;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class GraphQLExceptionHandler {

    @GraphQlExceptionHandler(AuthenticationException.class)
    public GraphQLError handleAuthenticationException() {
        GraphQLErrorResponse graphQLErrorResponse = GraphQLErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message("인증 오류가 발생했습니다.")
                .errorType(GraphQLErrorType.UNAUTHENTICATED)
                .extensions(graphQLErrorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(AccessDeniedException.class)
    public GraphQLError handleAccessDeniedException() {
        GraphQLErrorResponse graphQLErrorResponse = GraphQLErrorResponse.builder()
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message("권한으로 인한 인증 오류가 발생했습니다.")
                .errorType(GraphQLErrorType.FORBIDDEN)
                .extensions(graphQLErrorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(MethodArgumentNotValidException.class)
    public GraphQLError handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        GraphQLErrorResponse graphQLErrorResponse = GraphQLErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message(methodArgumentNotValidException.getBindingResult().getFieldError() != null
                                                ? methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage()
                                                : "Validation error occurred")
                .errorType(GraphQLErrorType.VALIDATION_ERROR)
                .extensions(graphQLErrorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(IllegalArgumentException.class)
    public GraphQLError handleIllegalArgumentException() {
        GraphQLErrorResponse graphQLErrorResponse = GraphQLErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message("잘못된 인자 값 입니다.")
                .errorType(GraphQLErrorType.VALIDATION_ERROR)
                .extensions(graphQLErrorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(HttpMessageNotReadableException.class)
    public GraphQLError handleHttpMessageNotReadableException() {
        GraphQLErrorResponse graphQLErrorResponse = GraphQLErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message("요청 본문을 읽을 수 없습니다.")
                .errorType(GraphQLErrorType.DATA_FETCHING_EXCEPTION)
                .extensions(graphQLErrorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(NuriBusinessException.class)
    public GraphQLError handleNuriBusinessException(NuriBusinessException nuriBusinessException) {
        GraphQLErrorResponse graphQLErrorResponse = GraphQLErrorResponse.builder()
                .code(nuriBusinessException.getStatus().getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message(nuriBusinessException.getMessage())
                .errorType(nuriBusinessException.getGraphQLErrorType())
                .extensions(graphQLErrorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(NuriSystemError.class)
    public GraphQLError handleNuriSystemError(NuriSystemError nuriSystemError) {
        loggingError(nuriSystemError);
        GraphQLErrorResponse graphQLErrorResponse = GraphQLErrorResponse.builder()
                .code(nuriSystemError.getStatus().getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message(nuriSystemError.getMessage())
                .errorType(GraphQLErrorType.INTERNAL_SERVER_ERROR)
                .extensions(graphQLErrorResponse.getMap())
                .build();
    }

    @GraphQlExceptionHandler(Exception.class)
    public GraphQLError handleException(Exception exception) {
        loggingError(exception);
        GraphQLErrorResponse graphQLErrorResponse = GraphQLErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();

        return GraphqlErrorBuilder.newError()
                .message("서버에서 예상치 못한 오류가 발생했습니다.")
                .errorType(GraphQLErrorType.INTERNAL_SERVER_ERROR)
                .extensions(graphQLErrorResponse.getMap())
                .build();
    }

    private void loggingError(Exception exception) {
        log.error("예외 발생 : [{}] - 메세지 : [{}]", exception.getClass().getName(), exception.getMessage());
    }
}

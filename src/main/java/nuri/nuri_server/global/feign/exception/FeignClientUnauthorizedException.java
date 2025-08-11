package nuri.nuri_server.global.feign.exception;

import nuri.nuri_server.global.exception.graphql.GraphQLErrorType;
import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class FeignClientUnauthorizedException extends NuriBusinessException {
    public FeignClientUnauthorizedException(String message,  HttpStatus httpStatus) {
        super(message, httpStatus, GraphQLErrorType.UNAUTHENTICATED);
    }
}

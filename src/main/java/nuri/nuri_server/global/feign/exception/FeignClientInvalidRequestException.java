package nuri.nuri_server.global.feign.exception;

import nuri.nuri_server.global.exception.graphql.GraphQLErrorType;
import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class FeignClientInvalidRequestException extends NuriBusinessException {
    public FeignClientInvalidRequestException(String message,  HttpStatus httpStatus) {
        super(message, httpStatus, GraphQLErrorType.BAD_REQUEST);
    }
}

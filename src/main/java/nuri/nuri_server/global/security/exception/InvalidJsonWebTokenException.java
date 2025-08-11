package nuri.nuri_server.global.security.exception;

import nuri.nuri_server.global.exception.graphql.GraphQLErrorType;
import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class InvalidJsonWebTokenException extends NuriBusinessException {
    public InvalidJsonWebTokenException() {
        super("올바르지 않은 JWT 토큰입니다.", HttpStatus.UNAUTHORIZED, GraphQLErrorType.UNAUTHENTICATED);
    }
}

package nuri.nuri_server.global.exception;

import lombok.Getter;
import nuri.nuri_server.global.exception.graphql.GraphQLErrorType;
import org.springframework.http.HttpStatus;

@Getter
public class NuriBusinessException extends NuriException {
    private final GraphQLErrorType graphQLErrorType;

    public NuriBusinessException(String message, HttpStatus status, GraphQLErrorType graphQLErrorType) {
        super(message, status);
        this.graphQLErrorType = graphQLErrorType;
    }
}

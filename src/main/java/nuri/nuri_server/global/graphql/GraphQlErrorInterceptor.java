package nuri.nuri_server.global.graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.ResponseError;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class GraphQlErrorInterceptor implements WebGraphQlInterceptor {

    @NotNull
    @Override
    public Mono<WebGraphQlResponse> intercept(@NotNull WebGraphQlRequest request, Chain chain) {
        return chain.next(request).map((response) -> {
            if (response.isValid()) return response;

            List<GraphQLError> errors = response.getErrors().stream()
                    .map(this::mapError)
                    .toList();

            return response.transform((builder) -> builder.errors(errors).build());
        });
    }

    private GraphQLError mapError(ResponseError error) {
        String msg = error.getMessage();

        if(msg == null) {
            return GraphqlErrorBuilder.newError()
                    .message("알 수 없는 에러가 발생했습니다.")
                    .errorType(error.getErrorType())
                    .path(Collections.singletonList(error.getPath()))
                    .locations(error.getLocations())
                    .build();
        }
        else if (msg.contains("Null value for non-null") && msg.contains("Validation error")) {
            return GraphqlErrorBuilder.newError()
                    .message("필수 입력값이 누락되었습니다.")
                    .path(Collections.singletonList(error.getPath()))
                    .errorType(error.getErrorType())
                    .locations(error.getLocations())
                    .build();
        }
        else {
            return GraphqlErrorBuilder.newError()
                    .message(error.getMessage())
                    .errorType(error.getErrorType())
                    .path(Collections.singletonList(error.getPath()))
                    .locations(error.getLocations())
                    .build();
        }
    }
}

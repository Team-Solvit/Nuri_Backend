package nuri.nuri_server.global.exception.graphql;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GraphQLErrorType implements ErrorClassification {

    BAD_REQUEST("BAD_REQUEST", "요청이 올바르지 않습니다."),
    UNAUTHENTICATED("UNAUTHENTICATED", "인증되지 않았습니다."),
    FORBIDDEN("FORBIDDEN", "접근이 거부되었습니다."),
    VALIDATION_ERROR("VALIDATION_ERROR", "검증과정에서 에러가 발생했습니다."),
    DATA_FETCHING_EXCEPTION("DATA_FETCHING_EXCEPTION", "데이터 처리과정에서 예외가 발생했습니다."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버에서 오류가 발생했습니다");

    private final String code;
    private final String description;

    @Override
    public Object toSpecification(GraphQLError error) {
        return ErrorClassification.super.toSpecification(error);
    }
}

package nuri.nuri_server.domain.chat.domain.exception;

import nuri.nuri_server.global.exception.NuriBusinessException;
import nuri.nuri_server.global.exception.graphql.GraphQLErrorType;
import org.springframework.http.HttpStatus;

public class UnauthorizedInvitationException extends NuriBusinessException {
    public UnauthorizedInvitationException() {
        super("초대권한이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED, GraphQLErrorType.UNAUTHENTICATED);
    }
}

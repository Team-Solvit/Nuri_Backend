package nuri.nuri_server.domain.user.domain.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ANONYMOUS("ROLE_ANONYMOUS"),
    USER("ROLE_USER"),
    INTERNATIONAL_STUDENT("ROLE_INTERNATIONAL_STUDENT"),
    HOST("ROLE_HOST"),
    THIRD_PARTY("ROLE_THIRD_PARTY"),
    OPERATOR("ROLE_OPERATOR");

    private final String value;
}

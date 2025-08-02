package nuri.nuri_server.domain.chat.domain.principal;

import lombok.AllArgsConstructor;

import java.security.Principal;

@AllArgsConstructor
public class ChatUserPrincipal implements Principal {
    private final String username;

    @Override
    public String getName() {
        return username;
    }
}

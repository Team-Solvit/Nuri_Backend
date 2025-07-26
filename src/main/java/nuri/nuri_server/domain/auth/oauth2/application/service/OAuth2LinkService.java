package nuri.nuri_server.domain.auth.oauth2.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.auth.oauth2.infra.builder.OAuthLinkBuilder;
import nuri.nuri_server.domain.auth.oauth2.application.service.exception.OAuthProviderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2LinkService {
    private final Map<String, OAuthLinkBuilder> linkBuilders;

    public String execute(String provider) {
        OAuthLinkBuilder linkBuilder = linkBuilders.get(provider + "_builder");
        if (linkBuilder == null) {
            throw new OAuthProviderNotFoundException(provider);
        }
        return linkBuilder.buildUrl();
    }
}

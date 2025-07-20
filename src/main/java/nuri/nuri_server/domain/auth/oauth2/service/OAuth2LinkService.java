package nuri.nuri_server.domain.auth.oauth2.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.auth.oauth2.builder.OAuthLinkBuilder;
import nuri.nuri_server.domain.auth.oauth2.service.exception.OAuthProviderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2LinkService {
    private final Map<String, OAuthLinkBuilder> linkBuilders;

    public String execute(String provider) {
        OAuthLinkBuilder linkBuilder = linkBuilders.get(provider);
        if (linkBuilder == null) {
            throw new OAuthProviderNotFoundException(provider);
        }
        return linkBuilder.buildUrl();
    }

    public String addStateUrl(String url, String state) {
        return url + "&state=" + state;
    }
}

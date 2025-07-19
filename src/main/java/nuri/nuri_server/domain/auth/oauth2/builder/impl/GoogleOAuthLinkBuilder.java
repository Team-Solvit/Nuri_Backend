package nuri.nuri_server.domain.auth.oauth2.builder.impl;

import nuri.nuri_server.domain.auth.oauth2.builder.OAuthLinkBuilder;
import org.springframework.stereotype.Component;

@Component("google")
public class GoogleOAuthLinkBuilder implements OAuthLinkBuilder {

    @Override
    public String buildUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth";
    }
}

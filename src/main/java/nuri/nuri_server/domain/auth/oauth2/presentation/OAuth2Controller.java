package nuri.nuri_server.domain.auth.oauth2.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    @QueryMapping
    public String getOAuth2Controller(@Argument String provider) {

    }
}

package nuri.nuri_server.domain.auth.oauth2.presentation;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TestResolver {
    @QueryMapping
    public String test() {
        return "ok";
    }
}

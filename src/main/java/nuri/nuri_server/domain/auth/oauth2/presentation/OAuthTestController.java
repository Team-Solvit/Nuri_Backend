package nuri.nuri_server.domain.auth.oauth2.presentation;

import nuri.nuri_server.domain.auth.oauth2.presentation.dto.res.SimpleMessageResponse;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class OAuthTestController {

    @MutationMapping
    public SimpleMessageResponse loginWithToken(@Argument String code) {
        System.out.println("🔐 받은 인가 코드: " + code);
        return new SimpleMessageResponse("코드 잘 받았습니다: " + code);
    }
}
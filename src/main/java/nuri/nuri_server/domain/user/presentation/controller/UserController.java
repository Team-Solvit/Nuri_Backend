package nuri.nuri_server.domain.user.presentation.controller;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.application.service.UserService;
import nuri.nuri_server.domain.user.presentation.dto.res.UserProfileResponseDto;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @QueryMapping
    public List<UserProfileResponseDto> queryUsers(@Argument String userId) {
        return userService.queryUsers(userId);
    }
}

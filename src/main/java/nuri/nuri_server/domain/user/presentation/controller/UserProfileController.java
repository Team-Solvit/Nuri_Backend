package nuri.nuri_server.domain.user.presentation.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.application.service.UserProfileService;
import nuri.nuri_server.domain.user.presentation.dto.res.UserProfileResponseDto;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @QueryMapping
    public UserProfileResponseDto getUserProfile(@Argument @NotNull(message="유저 아이디 값은 존재해야 합니다.") String userId) {
        return userProfileService.getProfile(userId);
    }

    @MutationMapping
    public Boolean changeProfile(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument @NotNull(message = "프로필 사진 링크가 존재해야 합니다.") String profile) {
        userProfileService.changeProfileImage(nuriUserDetails, profile);
        return true;
    }
}

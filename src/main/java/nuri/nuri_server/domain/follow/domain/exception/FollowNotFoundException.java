package nuri.nuri_server.domain.follow.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class FollowNotFoundException extends EntityNotFoundException {
    public FollowNotFoundException() {
        super("팔로우가 되어 있지 않습니다.");
    }
}

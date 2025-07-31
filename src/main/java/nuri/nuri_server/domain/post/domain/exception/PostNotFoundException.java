package nuri.nuri_server.domain.post.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {
    public PostNotFoundException() {
        super("게시물을 찾을 수 없습니다.");
    }
}

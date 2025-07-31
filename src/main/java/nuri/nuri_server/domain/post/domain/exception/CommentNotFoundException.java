package nuri.nuri_server.domain.post.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class CommentNotFoundException extends EntityNotFoundException {
    public CommentNotFoundException() {
        super("댓글을 찾을 수 없습니다.");
    }
}

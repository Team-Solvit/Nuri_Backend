package nuri.nuri_server.domain.follow.domain.exception;

import nuri.nuri_server.global.entity.exception.DuplicateEntityException;

public class DuplicateFollowException extends DuplicateEntityException {
  public DuplicateFollowException() {
    super("이미 팔로우가 되어 있습니다.");
  }
}

package nuri.nuri_server.domain.user.domain.exception;

import nuri.nuri_server.global.entity.exception.DuplicateEntityException;

public class DuplicateUserException extends DuplicateEntityException {
  public DuplicateUserException(String userId) {
    super("유저 아이디가 " + userId + "인 유저가 이미 존재합니다.");
  }
}

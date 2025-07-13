package nuri.nuri_server.domain.user.domain.exception;

import nuri.nuri_server.global.exception.entity.DuplicateEntityException;

public class DuplicateIdException extends DuplicateEntityException {
  public DuplicateIdException(String id) {
    super("유저 id :  " + id + " 을/를 사용중인 유저가 존재합니다.");
  }
}

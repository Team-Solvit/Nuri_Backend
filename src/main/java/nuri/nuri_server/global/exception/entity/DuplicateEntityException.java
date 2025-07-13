package nuri.nuri_server.global.exception.entity;

import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class DuplicateEntityException extends NuriBusinessException {
  public DuplicateEntityException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}

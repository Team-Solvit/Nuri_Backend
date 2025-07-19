package nuri.nuri_server.domain.user.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class LanguageNotFoundException extends EntityNotFoundException {
    public LanguageNotFoundException(String language) {
        super("언어 이름이 " + language + "인 언어는 지원하지 않습니다.");
    }
}

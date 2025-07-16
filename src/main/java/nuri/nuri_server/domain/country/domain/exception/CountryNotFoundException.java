package nuri.nuri_server.domain.country.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class CountryNotFoundException extends EntityNotFoundException {
    public CountryNotFoundException(String countryName) {
        super("나라 이름이 " + countryName + "인 나라가 존재하지 않습니다.");
    }
}

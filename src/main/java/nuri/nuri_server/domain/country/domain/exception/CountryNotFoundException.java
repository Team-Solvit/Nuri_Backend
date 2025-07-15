package nuri.nuri_server.domain.country.domain.exception;

import nuri.nuri_server.global.exception.entity.EntityNotFoundException;

public class CountryNotFoundException extends EntityNotFoundException {
    public CountryNotFoundException(String countryId) {
        super("나라 id가 " + countryId + "인 나라가 존재하지 않습니다.");
    }
}

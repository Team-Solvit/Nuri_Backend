package nuri.nuri_server.global.feign.google_map.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class AddressNotFoundException extends EntityNotFoundException {
    public AddressNotFoundException(String address) {
        super("주소가 " + address + "인 주소를 찾을 수 없습니다.");
    }
}

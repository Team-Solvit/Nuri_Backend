package nuri.nuri_server.domain.boarding_manage.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class BoardingManageWorkNotFoundException extends EntityNotFoundException {
    public BoardingManageWorkNotFoundException() {
        super("하숙관리 업무를 찾을 수 없습니다.");
    }
}

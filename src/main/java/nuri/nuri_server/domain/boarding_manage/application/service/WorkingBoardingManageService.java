package nuri.nuri_server.domain.boarding_manage.application.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_manage.domain.entity.BoardingManageWorkEntity;
import nuri.nuri_server.domain.boarding_manage.domain.exception.BoardingManageWorkNotFoundException;
import nuri.nuri_server.domain.boarding_manage.domain.repository.BoardingManageWorkRepository;
import nuri.nuri_server.domain.user.domain.entity.ThirdPartyEntity;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkingBoardingManageService {

    private final BoardingManageWorkRepository boardingManageWorkRepository;

    @Transactional
    public void completeBoardingManageWork(NuriUserDetails nuriUserDetails, UUID workId) {
        log.info("하숙관리 업무 완료 요청: userId={}, workId={}", nuriUserDetails.getId(), workId);

        BoardingManageWorkEntity manageWork = boardingManageWorkRepository.findById(workId)
                .orElseThrow(BoardingManageWorkNotFoundException::new);

        UserEntity user = nuriUserDetails.getUser();
        ThirdPartyEntity thirdParty = manageWork.getBoardingRelationship().getThirdParty();

        thirdParty.validate(user);

        manageWork.complete();

        log.info("하숙관리 업무 완료 반환: workId={}", workId);
    }

    @Transactional
    public void incompleteBoardingManageWork(NuriUserDetails nuriUserDetails,  UUID workId) {
        log.info("하숙관리 업무 완료 취소 요청: userId={}, workId={}", nuriUserDetails.getId(), workId);

        BoardingManageWorkEntity manageWork = boardingManageWorkRepository.findById(workId)
                .orElseThrow(BoardingManageWorkNotFoundException::new);

        UserEntity user = nuriUserDetails.getUser();
        ThirdPartyEntity thirdParty = manageWork.getBoardingRelationship().getThirdParty();

        thirdParty.validate(user);

        manageWork.incomplete();

        log.info("하숙관리 업무 완료 취소 반환: workId={}", workId);
    }
}

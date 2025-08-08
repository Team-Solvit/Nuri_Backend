package nuri.nuri_server.domain.boarding_house.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.domain.repository.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetBoardingHouseService {
    private final BoardingRoomRepository boardingRoomRepository;
    private final BoardingHouseRepository boardingHouseRepository;
    private final BoardingRoomFileRepository boardingRoomFileRepository;
    private final ContractPeriodRepository contractPeriodRepository;
    private final BoardingRoomOptionRepository boardingRoomOptionRepository;
}

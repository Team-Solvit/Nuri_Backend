package nuri.nuri_server.domain.boarding_house.domain.repository;

import nuri.nuri_server.domain.boarding_house.domain.entity.ContractPeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContractPeriodRepository extends JpaRepository<ContractPeriodEntity, UUID> {
    List<ContractPeriodEntity> findAllByBoardingRoomId(UUID boardingRoomId);
    void deleteAllByBoardingRoomId(UUID boardingRoomId);
}

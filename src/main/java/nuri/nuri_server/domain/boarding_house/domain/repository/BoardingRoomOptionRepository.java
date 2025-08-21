package nuri.nuri_server.domain.boarding_house.domain.repository;

import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardingRoomOptionRepository extends JpaRepository<BoardingRoomOptionEntity, UUID> {
    List<BoardingRoomOptionEntity> findAllByBoardingRoomId(UUID boardingRoomId);
    void deleteAllByBoardingRoomId(UUID boardingRoomId);
}

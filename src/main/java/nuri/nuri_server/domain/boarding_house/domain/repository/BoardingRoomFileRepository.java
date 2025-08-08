package nuri.nuri_server.domain.boarding_house.domain.repository;

import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardingRoomFileRepository extends JpaRepository<BoardingRoomFileEntity, UUID> {
    List<BoardingRoomFileEntity> findAllByBoardingRoomId(UUID boardingRoomId);
}

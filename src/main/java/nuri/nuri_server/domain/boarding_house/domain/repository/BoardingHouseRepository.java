package nuri.nuri_server.domain.boarding_house.domain.repository;

import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingHouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardingHouseRepository extends JpaRepository<BoardingHouseEntity, UUID> {
    @Query("select b from BoardingHouseEntity b where b.host.user.id = :hostId")
    Optional<BoardingHouseEntity> findByHostId(UUID hostId);
}

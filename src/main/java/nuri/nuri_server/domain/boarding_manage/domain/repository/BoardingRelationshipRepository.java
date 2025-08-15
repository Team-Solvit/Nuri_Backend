package nuri.nuri_server.domain.boarding_manage.domain.repository;

import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_manage.domain.entity.BoardingRelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardingRelationshipRepository extends JpaRepository<BoardingRelationshipEntity, UUID> {
    List<BoardingRelationshipEntity> findAllByThirdPartyId(UUID thirdPartyId);

    @Query("""
        select br.boardingRoom
        from BoardingRelationshipEntity br
        where br.boarderHouse.id = :houseId
    """)
    List<BoardingRoomEntity> findBoardingRoomByBoarderHouseId(UUID houseId);
}

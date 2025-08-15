package nuri.nuri_server.domain.boarding_manage.domain.repository;

import nuri.nuri_server.domain.boarding_manage.domain.entity.BoardingManageWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BoardingManageWorkRepository extends JpaRepository<BoardingManageWorkEntity, UUID> {
    @Query("select mw from BoardingManageWorkEntity mw " +
            "where mw.boardingRelationship.thirdParty.id = :thirdPartyId and " +
            "mw.date = :date")
    List<BoardingManageWorkEntity> findAllByThirdPartyIdAndDate(UUID thirdPartyId, LocalDate date);

    @Query("select mw from BoardingManageWorkEntity mw " +
            "where mw.boardingRelationship.thirdParty.id = :thirdPartyId and " +
            "mw.boardingRelationship.boardingHouse.id = :houseId and " +
            "mw.date = :date")
    List<BoardingManageWorkEntity> findAllByThirdPartyIdAndHouseIdAndDate(UUID thirdPartyId, UUID houseId, LocalDate date);
}

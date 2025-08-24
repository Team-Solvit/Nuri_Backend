package nuri.nuri_server.domain.boarding_house.domain.repository;

import nuri.nuri_server.domain.boarding_house.domain.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, UUID> {
    @Query("select c from ContractEntity c where c.boarderRoom.id = :roomId " +
            "and c.expiryDate > :date " +
            "and c.status = 'ACTIVE'")
    List<ContractEntity> findAllByRoomIdAndActive(@Param("roomId") UUID roomId, @Param("date") LocalDate date);
}

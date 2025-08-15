package nuri.nuri_server.domain.boarding_manage.domain.repository;

import nuri.nuri_server.domain.boarding_manage.domain.entity.BoardingManageWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardingManageWorkRepository extends JpaRepository<BoardingManageWorkEntity, UUID> {
}

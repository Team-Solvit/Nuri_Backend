package nuri.nuri_server.domain.boarding_house.domain.repository;

import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardingRoomCommentRepository extends JpaRepository<BoardingRoomCommentEntity, UUID> {
    Long countByBoardingRoomId(UUID postId);
    Page<BoardingRoomCommentEntity> findAllByBoardingRoomId(UUID postId, Pageable pageable);
}

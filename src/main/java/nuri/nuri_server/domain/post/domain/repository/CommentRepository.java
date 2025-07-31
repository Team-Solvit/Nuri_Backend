package nuri.nuri_server.domain.post.domain.repository;

import nuri.nuri_server.domain.post.domain.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
    Long countByPostId(UUID postId);
    Page<CommentEntity> findAllByPostId(UUID postId, Pageable pageable);
}

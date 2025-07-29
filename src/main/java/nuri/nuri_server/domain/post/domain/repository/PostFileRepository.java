package nuri.nuri_server.domain.post.domain.repository;

import nuri.nuri_server.domain.post.domain.entity.PostFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostFileRepository extends JpaRepository<PostFileEntity, UUID> {
}

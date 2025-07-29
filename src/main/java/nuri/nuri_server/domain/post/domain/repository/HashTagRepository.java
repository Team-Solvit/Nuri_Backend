package nuri.nuri_server.domain.post.domain.repository;

import nuri.nuri_server.domain.post.domain.entity.HashTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HashTagRepository extends JpaRepository<HashTagEntity, UUID> {
}

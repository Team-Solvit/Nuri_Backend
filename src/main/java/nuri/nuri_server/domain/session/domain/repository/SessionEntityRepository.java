package nuri.nuri_server.domain.session.domain.repository;

import nuri.nuri_server.domain.session.domain.entity.SessionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionEntityRepository extends CrudRepository<SessionEntity, String> {
}

package nuri.nuri_server.domain.refreshToken.repository;

import nuri.nuri_server.domain.refreshToken.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}

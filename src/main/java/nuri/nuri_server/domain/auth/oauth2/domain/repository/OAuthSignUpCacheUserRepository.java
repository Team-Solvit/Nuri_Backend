package nuri.nuri_server.domain.auth.oauth2.domain.repository;

import nuri.nuri_server.domain.auth.oauth2.domain.entity.OAuthSignUpCacheUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthSignUpCacheUserRepository extends CrudRepository<OAuthSignUpCacheUser, String> {
}

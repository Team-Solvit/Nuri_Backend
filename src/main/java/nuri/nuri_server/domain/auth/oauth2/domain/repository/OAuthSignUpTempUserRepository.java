package nuri.nuri_server.domain.auth.oauth2.domain.repository;

import nuri.nuri_server.domain.auth.oauth2.domain.entity.OAuthSignUpTempUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthSignUpTempUserRepository extends CrudRepository<OAuthSignUpTempUser, String> {
}

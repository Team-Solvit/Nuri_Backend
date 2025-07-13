package nuri.nuri_server.domain.country.domain.repository;

import nuri.nuri_server.domain.country.domain.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, String> {
}
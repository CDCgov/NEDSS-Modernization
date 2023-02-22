package gov.cdc.nbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.entity.odse.LocalUidGenerator;

public interface LocalUidGeneratorRepository extends JpaRepository<LocalUidGenerator, String> {

    public Optional<LocalUidGenerator> findByTypeCd(String typeCd);

}

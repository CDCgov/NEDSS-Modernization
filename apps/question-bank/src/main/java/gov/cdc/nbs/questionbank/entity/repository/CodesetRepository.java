package gov.cdc.nbs.questionbank.entity.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;

public interface CodesetRepository extends JpaRepository<Codeset, CodesetId> {

    Optional<Codeset> findOneByCodeSetGroupId(Long codeSetGroupId);
}

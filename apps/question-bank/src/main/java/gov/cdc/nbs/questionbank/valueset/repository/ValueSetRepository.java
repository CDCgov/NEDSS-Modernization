package gov.cdc.nbs.questionbank.valueset.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entities.ValueSet;

public interface ValueSetRepository extends JpaRepository<ValueSet, UUID> {
    Optional<ValueSet> findFirstByName(String name);
}

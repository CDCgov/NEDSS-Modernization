package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.srte.LoincCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoincCodeRepository extends JpaRepository<LoincCode, String> {

}

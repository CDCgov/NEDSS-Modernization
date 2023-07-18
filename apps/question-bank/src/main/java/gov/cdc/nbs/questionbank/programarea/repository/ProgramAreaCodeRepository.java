package gov.cdc.nbs.questionbank.programarea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.programarea.entity.ProgramAreaCode;

public interface ProgramAreaCodeRepository extends JpaRepository<ProgramAreaCode, String> {

}

package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.entity.srte.LabResult;
import gov.cdc.nbs.entity.srte.LabResultId;

public interface LabResultRepository extends JpaRepository<LabResult, LabResultId> {

}

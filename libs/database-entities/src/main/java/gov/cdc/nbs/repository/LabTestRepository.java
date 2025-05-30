package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.entity.srte.LabTest;
import gov.cdc.nbs.entity.srte.LabTestId;

public interface LabTestRepository extends JpaRepository<LabTest, LabTestId> {

}

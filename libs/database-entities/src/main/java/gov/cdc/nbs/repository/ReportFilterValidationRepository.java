package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.odse.ReportFilterValidation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportFilterValidationRepository
    extends JpaRepository<ReportFilterValidation, Long> {}

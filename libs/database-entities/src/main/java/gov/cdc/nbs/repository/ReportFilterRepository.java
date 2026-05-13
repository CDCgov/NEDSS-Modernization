package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportFilterRepository extends JpaRepository<ReportFilter, Long> {}

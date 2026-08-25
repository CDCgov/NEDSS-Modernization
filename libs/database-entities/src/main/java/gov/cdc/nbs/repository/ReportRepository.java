package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.repository.detach.DetachableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository
    extends JpaRepository<Report, ReportId>, DetachableRepository<Report> {}

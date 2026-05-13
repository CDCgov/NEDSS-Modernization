package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportLibraryRepository extends JpaRepository<ReportLibrary, Long> {}

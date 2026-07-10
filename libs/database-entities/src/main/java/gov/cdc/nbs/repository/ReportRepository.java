package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository
    extends JpaRepository<Report, ReportId>, DetachableRepository<Report> {
  @EntityGraph(value = "report.all-values")
  Optional<Report> findWithGraphById(ReportId id);
}

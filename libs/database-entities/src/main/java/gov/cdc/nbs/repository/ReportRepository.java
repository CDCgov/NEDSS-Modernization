package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.repository.detach.DetachableRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository
    extends DetachableRepository<Report>, JpaRepository<Report, ReportId> {
  @EntityGraph(value = "graph.Report.saveAs", type = EntityGraph.EntityGraphType.FETCH)
  Optional<Report> findWithGraphById(ReportId id);
}

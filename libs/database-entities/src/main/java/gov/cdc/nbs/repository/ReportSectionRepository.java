package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.odse.ReportSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportSectionRepository extends JpaRepository<ReportSection, Long> {

  boolean existsBySectionCd(String sectionCd);
}

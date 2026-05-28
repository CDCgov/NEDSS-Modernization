package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, ReportId> {
    /**
     * Generates the next available `report_uid` in the DB; to be used
     * during report creation.<br><br>
     * Necessary because `report_uid` is not marked as an IDENTITY column, nor
     * does it have a corresponding sequence, so we have to use a custom query.
     */
    @Query("SELECT (MAX(r.id.reportUid)+1) FROM Report r")
    Long getNextReportId();
}

package gov.cdc.nbs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.srte.LabResult;
import gov.cdc.nbs.entity.srte.LabResultId;

public interface LabResultRepository extends JpaRepository<LabResult, LabResultId> {

    // query from NBS code - ObservationProcessor.java line 671
    @Query("SELECT lr FROM LabResult lr WHERE lr.organismNameInd = 'N' AND (lr.labResultDescTxt like %:searchString% OR lr.id.labResultCd like %:searchString%)")
    Page<LabResult> findLabResults(@Param("searchString") String searchString, Pageable pageable);

    @Query("SELECT DISTINCT lr.labResultDescTxt FROM LabResult lr WHERE lr.organismNameInd = 'N' AND (lr.labResultDescTxt like :searchString% OR lr.id.labResultCd like :searchString%)")
    Page<String> findDistinctLabResults(@Param("searchString") String searchString, Pageable pageable);

}

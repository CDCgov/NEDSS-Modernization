package gov.cdc.nbs.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.srte.LoincCode;

public interface LoincCodeRepository extends JpaRepository<LoincCode, String> {

    // query from NBS code - ObservationProcessor.java line 697
    @Query("SELECT lc FROM LoincCode lc WHERE lc.relatedClassCd IN (:relatedClassCodes) AND (UPPER(lc.componentName) like %:searchString% OR UPPER(lc.id) like %:searchString%)")
    Page<LoincCode> findTest(
            @Param("searchString") String searchString,
            @Param("relatedClassCodes") List<String> relatedClassCodes,
            Pageable pageable);

    @Query("SELECT DISTINCT lc.componentName FROM LoincCode lc WHERE lc.relatedClassCd IN (:relatedClassCodes) AND (UPPER(lc.componentName) like :searchString% OR UPPER(lc.id) like :searchString%)")
    Page<String> findDistinctTestNames(
            @Param("searchString") String searchString,
            @Param("relatedClassCodes") List<String> relatedClassCodes,
            Pageable pageable);
}

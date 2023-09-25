package gov.cdc.nbs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.srte.LabTest;
import gov.cdc.nbs.entity.srte.LabTestId;

public interface LabTestRepository extends JpaRepository<LabTest, LabTestId> {

    // query from NBS code - ObservationProcessor.java line 671
    @Query("SELECT lt FROM LabTest lt WHERE lt.testTypeCd = 'R' AND (UPPER(lt.labTestDescTxt) like %:searchString% OR UPPER(lt.id.labTestCd) like %:searchString%)")
    Page<LabTest> findTests(@Param("searchString") String searchString, Pageable pageable);

    @Query("SELECT DISTINCT lt.labTestDescTxt FROM LabTest lt WHERE lt.testTypeCd = 'R' AND (UPPER(lt.labTestDescTxt) like :searchString% OR UPPER(lt.id.labTestCd) like :searchString%)")
    Page<String> findDistinctTestNames(@Param("searchString") String searchString, Pageable pageable);

}

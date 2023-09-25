package gov.cdc.nbs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.srte.SnomedCode;

public interface SnomedCodeRepository extends JpaRepository<SnomedCode, String> {

    @Query("SELECT sc FROM SnomedCode sc WHERE sc.snomedDescTxt LIKE %:searchString% OR sc.id LIKE %:searchString%")
    Page<SnomedCode> findSnomedCodes(@Param("searchString") String searchString, Pageable pageable);

    @Query("SELECT DISTINCT sc.snomedDescTxt FROM SnomedCode sc WHERE sc.snomedDescTxt LIKE :searchString% OR sc.id LIKE :searchString%")
    Page<String> findDistinctSnomedCodes(@Param("searchString") String searchString, Pageable pageable);

}

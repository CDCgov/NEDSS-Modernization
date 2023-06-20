package gov.cdc.nbs.questionbank.valueset.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralId;



public interface CodeValueGeneralRepository extends JpaRepository<CodeValueGeneral, CodeValueGeneralId> {

    @Query("SELECT cvg FROM CodeValueGeneral cvg WHERE cvg.id.codeSetNm=:name")
    Page<CodeValueGeneral> findAllByCodeSetName(@Param("name") String name, Pageable pageable);
}

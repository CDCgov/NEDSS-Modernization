package gov.cdc.nbs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.entity.srte.CodeValueGeneralId;

public interface CodeValueGeneralRepository extends JpaRepository<CodeValueGeneral, CodeValueGeneralId> {

  @Query("SELECT cvg FROM CodeValueGeneral cvg WHERE cvg.id.codeSetNm=:name")
  Page<CodeValueGeneral> findAllByCodeSetName(@Param("name") String name, Pageable pageable);

  @Query("SELECT cvg FROM CodeValueGeneral cvg WHERE cvg.id.codeSetNm=:name order by cvg.id.code asc")
  Page<CodeValueGeneral> findAllByCodeSetNameOrdered(@Param("name") String name, Pageable pageable);
}

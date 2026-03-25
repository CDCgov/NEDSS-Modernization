package gov.cdc.nbs.questionbank.entity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CodeValueGeneralRepository
    extends JpaRepository<CodeValueGeneral, CodeValueGeneralId> {

  @Query(
      "SELECT cvg FROM CodeValueGeneral cvg WHERE cvg.id.code=:code AND cvg.id.codeSetNm = 'CODE_SYSTEM'")
  Optional<CodeValueGeneral> findCodeSystemByCode(@Param("code") String code);

  Optional<CodeValueGeneral> findByIdCodeSetNmAndIdCode(String codesetName, String code);

  List<CodeValueGeneral> findByIdCodeSetNm(String codeSetNm, Sort sort);

  @Modifying
  @Query("DELETE from CodeValueGeneral c WHERE c.id.codeSetNm =:codesetNm")
  void deleteAllByCodesetName(@Param("codesetNm") String codesetNm);
}

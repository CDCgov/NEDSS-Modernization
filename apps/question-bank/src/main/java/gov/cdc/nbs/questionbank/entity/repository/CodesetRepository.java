package gov.cdc.nbs.questionbank.entity.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;

public interface CodesetRepository extends JpaRepository<Codeset, CodesetId> {

    Optional<Codeset> findOneByCodeSetGroupId(Long codeSetGroupId);

    @Query("SELECT c FROM Codeset c WHERE c.id.codeSetNm =:codeset AND c.id.classCd = 'code_value_general'")
    Optional<Codeset> findByCodeSetName(@Param("codeset") String codeset);
}

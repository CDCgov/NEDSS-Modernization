package gov.cdc.nbs.questionbank.valueset.repository;

import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodesetGroupMetadatumRepository
    extends JpaRepository<CodeSetGroupMetadatum, Long> {

  @Query("SELECT MAX(id) FROM CodeSetGroupMetadatum")
  long getCodeSetGroupMaxID();
}

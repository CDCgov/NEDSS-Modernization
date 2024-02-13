package gov.cdc.nbs.questionbank.valueset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;

public interface CodesetGroupMetadatumRepository extends JpaRepository<CodeSetGroupMetadatum, Long> {

  @Query("SELECT MAX(id) FROM CodeSetGroupMetadatum")
  long getCodeSetGroupMaxID();

}

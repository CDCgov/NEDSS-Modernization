package gov.cdc.nbs.questionbank.entity.repository;

import gov.cdc.nbs.questionbank.entity.WaNndMetadatum;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WANNDMetadataRepository extends JpaRepository<WaNndMetadatum, Long> {

  public void deleteByWaTemplateUid(WaTemplate page);

  List<WaNndMetadatum> findAllByWaTemplateUid(WaTemplate templateId);
}

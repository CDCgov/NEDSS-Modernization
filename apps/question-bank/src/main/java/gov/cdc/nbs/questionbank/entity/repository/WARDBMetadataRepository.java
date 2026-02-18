package gov.cdc.nbs.questionbank.entity.repository;

import gov.cdc.nbs.questionbank.entity.WaRdbMetadata;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WARDBMetadataRepository extends JpaRepository<WaRdbMetadata, Long> {

  public void deleteByWaTemplateUid(WaTemplate page);

  List<WaRdbMetadata> findAllByWaTemplateUid(WaTemplate templateId);
}

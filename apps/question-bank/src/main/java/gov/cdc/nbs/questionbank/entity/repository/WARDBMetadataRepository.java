package gov.cdc.nbs.questionbank.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entity.WaRdbMetadata;
import gov.cdc.nbs.questionbank.entity.WaTemplate;

import java.util.List;

public interface WARDBMetadataRepository extends JpaRepository<WaRdbMetadata, Long> {

	public void deleteByWaTemplateUid(WaTemplate page);

	List<WaRdbMetadata> findAllByWaTemplateUid(WaTemplate templateId);

}

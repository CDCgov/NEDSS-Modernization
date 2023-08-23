package gov.cdc.nbs.questionbank.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.questionbank.entity.WaNndMetadatum;
import gov.cdc.nbs.questionbank.entity.WaTemplate;

public interface WANNDMetadataRepository extends JpaRepository<WaNndMetadatum, Long> {

	public void deleteByWaTemplateUid(WaTemplate page);
}

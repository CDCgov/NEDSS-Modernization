package gov.cdc.nbs.questionbank.businessrules.repository;

import gov.cdc.nbs.questionbank.entity.businessRule.WaRuleMetadata;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WaRuleMetaDataRepository extends JpaRepository<WaRuleMetadata, String> {
}

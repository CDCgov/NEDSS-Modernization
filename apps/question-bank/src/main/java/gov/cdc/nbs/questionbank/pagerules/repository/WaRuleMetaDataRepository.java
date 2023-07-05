package gov.cdc.nbs.questionbank.pagerules.repository;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WaRuleMetaDataRepository extends JpaRepository<WaRuleMetadata, Long> {

//    @Query("DELETE * from WaRuleMetadata r where r.wa_rule_metadata_uid=:ruleId")
//    void deleteRuleByRuleId(
//            @Param("ruleId") String ruleId
//    );

//    @Query("Select * from WaRuleMetadata r where r.wa_rule_metadata_uid=:ruleId")
//    WaRuleMetadata findByRuleId(
//            @Param("ruleId") String ruleId
//    );
}

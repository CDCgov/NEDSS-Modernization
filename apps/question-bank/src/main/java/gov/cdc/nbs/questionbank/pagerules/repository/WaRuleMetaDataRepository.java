package gov.cdc.nbs.questionbank.pagerules.repository;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WaRuleMetaDataRepository extends JpaRepository<WaRuleMetadata, Long> {

  void deleteByWaTemplateUid(Long pageId);

  public List<WaRuleMetadata> findByWaTemplateUid(Long pageId);

  public Page<WaRuleMetadata> findByWaTemplateUid(Long pageId, Pageable pageable);

  public Page<WaRuleMetadata>
      findAllBySourceValuesContainingIgnoreCaseOrTargetQuestionIdentifierContainingIgnoreCase(
          String sourceValue, String targetValue, Pageable pageable);

  @Query("select MAX(id) + 1 from WaRuleMetadata")
  Long findNextAvailableID();
}

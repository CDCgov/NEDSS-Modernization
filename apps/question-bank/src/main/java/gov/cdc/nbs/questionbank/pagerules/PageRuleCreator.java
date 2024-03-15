package gov.cdc.nbs.questionbank.pagerules;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;

@Component
@Transactional
public class PageRuleCreator {

  private final WaRuleMetaDataRepository repository;
  private final DateCompareCreator dateCompareCreator;
  private final EnableDisableCreator enableDisableCreator;
  private final HideUnhideCreator hideUnhideCreator;

  public PageRuleCreator(
      final WaRuleMetaDataRepository waRuleMetaDataRepository,
      final DateCompareCreator dateCompareCreator,
      final EnableDisableCreator enableDisableCreator,
      final HideUnhideCreator hideUnhideCreator) {
    this.repository = waRuleMetaDataRepository;
    this.dateCompareCreator = dateCompareCreator;
    this.enableDisableCreator = enableDisableCreator;
    this.hideUnhideCreator = hideUnhideCreator;
  }

  public Rule createPageRule(Long userId, RuleRequest request, long page) {
    long availableId = repository.findNextAvailableID();
    WaRuleMetadata ruleMetadata = switch (request.ruleFunction()) {
      case DATE_COMPARE -> dateCompareCreator.create(availableId, request, page, userId);
      case DISABLE, ENABLE -> enableDisableCreator.create(availableId, request, page, userId);
      case HIDE, UNHIDE -> hideUnhideCreator.create(availableId, request, page, userId);
      case REQUIRE_IF -> null;
      default -> throw new RuleException("Unsupported function specified");
    };

    repository.save(ruleMetadata);
    return null;
  }
}

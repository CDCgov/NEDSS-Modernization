package gov.cdc.nbs.questionbank.pagerules;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import gov.cdc.nbs.questionbank.valueset.concept.ConceptFinder;
import gov.cdc.nbs.questionbank.valueset.model.Concept;

@Component
@Transactional
public class PageRuleCreator {

  private final WaRuleMetaDataRepository repository;
  private final DateCompareCreator dateCompareCreator;
  private final EnableDisableCreator enableDisableCreator;
  private final HideUnhideCreator hideUnhideCreator;
  private final RequireIfCreator requireIfCreator;
  private final PageRuleFinder finder;
  private final ConceptFinder conceptFinder;
  private final EntityManager entityManager;

  public PageRuleCreator(
      final WaRuleMetaDataRepository waRuleMetaDataRepository,
      final DateCompareCreator dateCompareCreator,
      final EnableDisableCreator enableDisableCreator,
      final HideUnhideCreator hideUnhideCreator,
      final RequireIfCreator requireIfCreator,
      final PageRuleFinder finder,
      final ConceptFinder conceptFinder,
      final EntityManager entityManager) {
    this.repository = waRuleMetaDataRepository;
    this.dateCompareCreator = dateCompareCreator;
    this.enableDisableCreator = enableDisableCreator;
    this.hideUnhideCreator = hideUnhideCreator;
    this.requireIfCreator = requireIfCreator;
    this.finder = finder;
    this.conceptFinder = conceptFinder;
    this.entityManager = entityManager;
  }

  public Rule createPageRule(RuleRequest request, long page, Long userId) {

    WaTemplate template = entityManager.find(WaTemplate.class, page);
    if (template == null) {
      throw new RuleException("Invalid page specified");
    }
    if (!"Draft".equals(template.getTemplateType())) {
      throw new RuleException("Unable to modify non Draft page");
    }

    // Souce values are empty if `any source value` is selected, we need to populate those
    if (request.anySourceValue()) {
      request = addSourceValues(request, page);
    }

    long availableId = repository.findNextAvailableID();
    PageContentCommand.AddRuleCommand command = switch (request.ruleFunction()) {
      case DATE_COMPARE -> dateCompareCreator.create(availableId, request, page, userId);
      case DISABLE, ENABLE -> enableDisableCreator.create(availableId, request, page, userId);
      case HIDE, UNHIDE -> hideUnhideCreator.create(availableId, request, page, userId);
      case REQUIRE_IF -> requireIfCreator.create(availableId, request, page, userId);
      default -> throw new RuleException("Unsupported function specified");
    };

    WaRuleMetadata ruleMetadata = repository.save(new WaRuleMetadata(command));
    return finder.findByRuleId(ruleMetadata.getId());
  }

  RuleRequest addSourceValues(RuleRequest request, long page) {
    List<Concept> concepts = conceptFinder.findByQuestionIdentifier(request.sourceIdentifier(), page);
    List<SourceValue> sourceValues = concepts.stream()
        .map(c -> new SourceValue(c.localCode(), c.display()))
        .toList();
    return new RuleRequest(
        request.ruleFunction(),
        request.description(),
        request.sourceIdentifier(),
        request.anySourceValue(),
        sourceValues,
        request.comparator(),
        request.targetType(),
        request.targetIdentifiers(),
        request.sourceText(),
        request.targetValueText());
  }
}

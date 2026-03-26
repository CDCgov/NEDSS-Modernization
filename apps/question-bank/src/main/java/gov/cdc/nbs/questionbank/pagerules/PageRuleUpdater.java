package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import gov.cdc.nbs.questionbank.valueset.concept.ConceptFinder;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class PageRuleUpdater {

  private final EntityManager entityManager;
  private final ConceptFinder conceptFinder;
  private final PageRuleFinder finder;
  private final DateCompareCommandCreator dateCompareCreator;
  private final EnableDisableCommandCreator enableDisableCreator;
  private final HideUnhideCommandCreator hideUnhideCreator;
  private final RequireIfCommandCreator requireIfCreator;

  public PageRuleUpdater(
      final EntityManager entityManager,
      final ConceptFinder conceptFinder,
      final DateCompareCommandCreator dateCompareCreator,
      final EnableDisableCommandCreator enableDisableCreator,
      final HideUnhideCommandCreator hideUnhideCreator,
      final RequireIfCommandCreator requireIfCreator,
      final PageRuleFinder finder) {
    this.entityManager = entityManager;
    this.conceptFinder = conceptFinder;
    this.dateCompareCreator = dateCompareCreator;
    this.enableDisableCreator = enableDisableCreator;
    this.hideUnhideCreator = hideUnhideCreator;
    this.requireIfCreator = requireIfCreator;
    this.finder = finder;
  }

  public Rule updatePageRule(Long ruleId, RuleRequest request, Long user) {

    WaRuleMetadata rule = entityManager.find(WaRuleMetadata.class, ruleId);
    if (rule == null) {
      throw new RuleException("Failed to find rule");
    }

    WaTemplate template = entityManager.find(WaTemplate.class, rule.getWaTemplateUid());
    if (template == null) {
      throw new RuleException("Invalid page specified");
    }
    if (!"Draft".equals(template.getTemplateType())) {
      throw new RuleException("Unable to modify non Draft page");
    }

    // Souce values are empty if `any source value` is selected, we need to populate those
    if (request.anySourceValue()) {
      request = addSourceValues(request, template.getId());
    }

    PageContentCommand.UpdateRuleCommand command =
        switch (request.ruleFunction()) {
          case DATE_COMPARE -> dateCompareCreator.update(ruleId, request, user);
          case DISABLE, ENABLE -> enableDisableCreator.update(ruleId, request, user);
          case HIDE, UNHIDE -> hideUnhideCreator.update(ruleId, request, user);
          case REQUIRE_IF -> requireIfCreator.update(ruleId, request, user);
          default -> throw new RuleException("Unsupported function specified");
        };

    rule.update(command);
    entityManager.flush();
    return finder.findByRuleId(rule.getId());
  }

  RuleRequest addSourceValues(RuleRequest request, long page) {
    List<Concept> concepts =
        conceptFinder.findByQuestionIdentifier(request.sourceIdentifier(), page);
    List<SourceValue> sourceValues =
        concepts.stream().map(c -> new SourceValue(c.localCode(), c.display())).toList();
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

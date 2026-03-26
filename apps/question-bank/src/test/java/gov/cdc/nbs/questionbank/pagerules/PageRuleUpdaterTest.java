package gov.cdc.nbs.questionbank.pagerules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.pagerules.Rule.Comparator;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.Rule.TargetType;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import gov.cdc.nbs.questionbank.valueset.concept.ConceptFinder;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageRuleUpdaterTest {

  @Mock private EntityManager entityManager;
  @Mock private ConceptFinder conceptFinder;
  @Mock private PageRuleFinder finder;
  @Mock private DateCompareCommandCreator dateCompareCreator;
  @Mock private EnableDisableCommandCreator enableDisableCreator;
  @Mock private HideUnhideCommandCreator hideUnhideCreator;
  @Mock private RequireIfCommandCreator requireIfCreator;

  @InjectMocks private PageRuleUpdater updater;

  @Test
  void should_fail_invalid_rule() {
    when(entityManager.find(WaRuleMetadata.class, 1L)).thenReturn(null);

    assertThrows(RuleException.class, () -> updater.updatePageRule(1L, null, 3L));
  }

  @Test
  void should_fail_null_page() {
    WaRuleMetadata mockRule = Mockito.mock(WaRuleMetadata.class);
    when(mockRule.getWaTemplateUid()).thenReturn(78L);
    when(entityManager.find(WaRuleMetadata.class, 1L)).thenReturn(mockRule);

    when(entityManager.find(WaTemplate.class, 78L)).thenReturn(null);

    assertThrows(RuleException.class, () -> updater.updatePageRule(1L, null, 3L));
  }

  @Test
  void should_fail_published_page() {
    WaRuleMetadata mockRule = Mockito.mock(WaRuleMetadata.class);
    when(mockRule.getWaTemplateUid()).thenReturn(78L);
    when(entityManager.find(WaRuleMetadata.class, 1L)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Published");
    when(entityManager.find(WaTemplate.class, 78L)).thenReturn(template);

    assertThrows(RuleException.class, () -> updater.updatePageRule(1L, null, 3L));
  }

  @Test
  void should_set_source_values() {
    RuleRequest request =
        new RuleRequest(
            RuleFunction.ENABLE, "desc", "source", true, null, null, null, null, null, null);

    when(conceptFinder.findByQuestionIdentifier("source", 1L))
        .thenReturn(
            List.of(
                new Concept(
                    null,
                    "localCode",
                    null,
                    "display",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null)));
    RuleRequest updatedRequest = updater.addSourceValues(request, 1L);
    assertThat(updatedRequest.sourceValues()).isNotEmpty();
  }

  @Test
  void enable() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99L);
    when(rule.getWaTemplateUid()).thenReturn(66L);
    when(entityManager.find(WaRuleMetadata.class, 99L)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99L)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66L)).thenReturn(template);

    RuleRequest request =
        new RuleRequest(
            RuleFunction.ENABLE,
            "desc",
            "source",
            false,
            List.of(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            List.of("INV123"),
            "Source question",
            List.of("Target quest"));

    when(enableDisableCreator.update(99L, request, 1L)).thenReturn(command());
    updater.updatePageRule(99L, request, 1L);
    verify(entityManager).flush();
  }

  @Test
  void disable() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99L);
    when(rule.getWaTemplateUid()).thenReturn(66L);
    when(entityManager.find(WaRuleMetadata.class, 99L)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99L)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66L)).thenReturn(template);

    RuleRequest request =
        new RuleRequest(
            RuleFunction.DISABLE,
            "desc",
            "source",
            false,
            List.of(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            List.of("INV123"),
            "Source question",
            List.of("Target quest"));
    when(enableDisableCreator.update(99L, request, 1L)).thenReturn(command());

    updater.updatePageRule(99L, request, 1L);
    verify(entityManager).flush();
  }

  @Test
  void date() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99L);
    when(rule.getWaTemplateUid()).thenReturn(66L);
    when(entityManager.find(WaRuleMetadata.class, 99L)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99L)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66L)).thenReturn(template);

    RuleRequest request =
        new RuleRequest(
            RuleFunction.DATE_COMPARE,
            "desc",
            "source",
            false,
            List.of(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            List.of("INV123"),
            "Source question",
            List.of("Target quest"));
    when(dateCompareCreator.update(99L, request, 1L)).thenReturn(command());

    updater.updatePageRule(99L, request, 1L);
    verify(entityManager).flush();
  }

  @Test
  void hide() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99L);
    when(rule.getWaTemplateUid()).thenReturn(66L);
    when(entityManager.find(WaRuleMetadata.class, 99L)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99L)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66L)).thenReturn(template);

    RuleRequest request =
        new RuleRequest(
            RuleFunction.HIDE,
            "desc",
            "source",
            false,
            List.of(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            List.of("INV123"),
            "Source question",
            List.of("Target quest"));
    when(hideUnhideCreator.update(99L, request, 1L)).thenReturn(command());

    updater.updatePageRule(99L, request, 1L);
    verify(entityManager).flush();
  }

  @Test
  void unhide() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99L);
    when(rule.getWaTemplateUid()).thenReturn(66L);
    when(entityManager.find(WaRuleMetadata.class, 99L)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99L)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66L)).thenReturn(template);

    RuleRequest request =
        new RuleRequest(
            RuleFunction.UNHIDE,
            "desc",
            "source",
            false,
            List.of(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            List.of("INV123"),
            "Source question",
            List.of("Target quest"));
    when(hideUnhideCreator.update(99L, request, 1L)).thenReturn(command());

    updater.updatePageRule(99L, request, 1L);
    verify(entityManager).flush();
  }

  @Test
  void require_if() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99L);
    when(rule.getWaTemplateUid()).thenReturn(66L);
    when(entityManager.find(WaRuleMetadata.class, 99L)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99L)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66L)).thenReturn(template);

    RuleRequest request =
        new RuleRequest(
            RuleFunction.REQUIRE_IF,
            "desc",
            "source",
            false,
            List.of(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            List.of("INV123"),
            "Source question",
            List.of("Target quest"));
    when(requireIfCreator.update(99L, request, 1L)).thenReturn(command());

    updater.updatePageRule(99L, request, 1L);
    verify(entityManager).flush();
  }

  private PageContentCommand.UpdateRuleCommand command() {
    return new PageContentCommand.UpdateRuleCommand(
        "QUESTION",
        "description",
        "=",
        "sourceIdent",
        "sourceValues",
        "targetIdent",
        "errorMessage",
        "javascript",
        "js name",
        "expression",
        1L,
        Instant.now());
  }
}

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
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import gov.cdc.nbs.questionbank.valueset.concept.ConceptFinder;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageRuleCreatorTest {

  @Mock private WaRuleMetaDataRepository repository;
  @Mock private DateCompareCommandCreator dateCompareCreator;
  @Mock private EnableDisableCommandCreator enableDisableCreator;
  @Mock private HideUnhideCommandCreator hideUnhideCreator;
  @Mock private RequireIfCommandCreator requireIfCreator;
  @Mock private PageRuleFinder finder;
  @Mock private ConceptFinder conceptFinder;
  @Mock private EntityManager entityManager;

  @InjectMocks private PageRuleCreator creator;

  @Test
  void should_fail_invalid_page() {
    RuleRequest request =
        new RuleRequest(
            null, null, null, false, null, null, null, Arrays.asList("test"), null, null);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    assertThrows(RuleException.class, () -> creator.createPageRule(request, 1l, 3l));
  }

  @Test
  void should_fail_published_page() {
    RuleRequest request =
        new RuleRequest(
            null, null, null, false, null, null, null, Arrays.asList("test"), null, null);
    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Published");
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(template);

    assertThrows(RuleException.class, () -> creator.createPageRule(request, 1l, 3l));
  }

  @Test
  void should_set_source_values() {
    RuleRequest request =
        new RuleRequest(
            RuleFunction.ENABLE, "desc", "source", true, null, null, null, null, null, null);

    when(conceptFinder.findByQuestionIdentifier("source", 1l))
        .thenReturn(
            Arrays.asList(
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
    RuleRequest updatedRequest = creator.addSourceValues(request, 1l);
    assertThat(updatedRequest.sourceValues()).isNotEmpty();
  }

  @Test
  void enable() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99l)).thenReturn(mockRule);
    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(template);
    when(repository.findNextAvailableID()).thenReturn(99l);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.ENABLE,
            "desc",
            "source",
            false,
            Arrays.asList(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("INV123"),
            "Source question",
            Arrays.asList("Target quest"));
    when(enableDisableCreator.create(99l, request, 1l, 2l)).thenReturn(command(99l));
    when(repository.save(Mockito.any())).thenReturn(rule);
    creator.createPageRule(request, 1l, 2l);
    verify(repository).save(Mockito.any());
  }

  @Test
  void disable() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99l)).thenReturn(mockRule);
    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(template);
    when(repository.findNextAvailableID()).thenReturn(99l);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.DISABLE,
            "desc",
            "source",
            false,
            Arrays.asList(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("INV123"),
            "Source question",
            Arrays.asList("Target quest"));
    when(enableDisableCreator.create(99l, request, 1l, 2l)).thenReturn(command(99l));
    when(repository.save(Mockito.any())).thenReturn(rule);
    creator.createPageRule(request, 1l, 2l);
    verify(repository).save(Mockito.any());
  }

  @Test
  void date_compare() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99l)).thenReturn(mockRule);
    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(template);
    when(repository.findNextAvailableID()).thenReturn(99l);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.DATE_COMPARE,
            "desc",
            "source",
            false,
            Arrays.asList(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("INV123"),
            "Source question",
            Arrays.asList("Target quest"));
    when(dateCompareCreator.create(99l, request, 1l, 2l)).thenReturn(command(99l));
    when(repository.save(Mockito.any())).thenReturn(rule);
    creator.createPageRule(request, 1l, 2l);
    verify(repository).save(Mockito.any());
  }

  @Test
  void hide() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99l)).thenReturn(mockRule);
    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(template);
    when(repository.findNextAvailableID()).thenReturn(99l);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.HIDE,
            "desc",
            "source",
            false,
            Arrays.asList(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("INV123"),
            "Source question",
            Arrays.asList("Target quest"));
    when(hideUnhideCreator.create(99l, request, 1l, 2l)).thenReturn(command(99l));
    when(repository.save(Mockito.any())).thenReturn(rule);
    creator.createPageRule(request, 1l, 2l);
    verify(repository).save(Mockito.any());
  }

  @Test
  void unhide() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99l)).thenReturn(mockRule);
    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(template);
    when(repository.findNextAvailableID()).thenReturn(99l);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.UNHIDE,
            "desc",
            "source",
            false,
            Arrays.asList(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("INV123"),
            "Source question",
            Arrays.asList("Target quest"));
    when(hideUnhideCreator.create(99l, request, 1l, 2l)).thenReturn(command(99l));
    when(repository.save(Mockito.any())).thenReturn(rule);
    creator.createPageRule(request, 1l, 2l);
    verify(repository).save(Mockito.any());
  }

  @Test
  void require_if() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findByRuleId(99l)).thenReturn(mockRule);
    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(template);
    when(repository.findNextAvailableID()).thenReturn(99l);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.REQUIRE_IF,
            "desc",
            "source",
            false,
            Arrays.asList(new SourceValue("A", "B")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("INV123"),
            "Source question",
            Arrays.asList("Target quest"));
    when(requireIfCreator.create(99l, request, 1l, 2l)).thenReturn(command(99l));
    when(repository.save(Mockito.any())).thenReturn(rule);
    creator.createPageRule(request, 1l, 2l);
    verify(repository).save(Mockito.any());
  }

  @Test
  void should_reject_null_request() {
    RuleRequest request = null;
    assertThrows(RuleException.class, () -> creator.createPageRule(request, 0, null));
  }

  @Test
  void should_reject_null_targets() {
    RuleRequest request =
        new RuleRequest(null, null, null, false, null, null, null, null, null, null);
    assertThrows(RuleException.class, () -> creator.createPageRule(request, 0, null));
  }

  @Test
  void should_reject_empty_targets() {
    RuleRequest request =
        new RuleRequest(null, null, null, false, null, null, null, new ArrayList<>(), null, null);
    assertThrows(RuleException.class, () -> creator.createPageRule(request, 0, null));
  }

  @Test
  void should_reject_blank_targets() {
    RuleRequest request =
        new RuleRequest(
            null, null, null, false, null, null, null, Arrays.asList("", "test"), null, null);
    assertThrows(RuleException.class, () -> creator.createPageRule(request, 0, null));
  }

  @Test
  void should_reject_null_target_id() {
    RuleRequest request =
        new RuleRequest(
            null, null, null, false, null, null, null, Arrays.asList(null, "test"), null, null);
    assertThrows(RuleException.class, () -> creator.createPageRule(request, 0, null));
  }

  private PageContentCommand.AddRuleCommand command(long id) {
    return new PageContentCommand.AddRuleCommand(
        id,
        "QUESTION",
        "ENABLE",
        "description",
        "=",
        "sourceIdent",
        "sourceValues",
        "targetIdent",
        "errorMessage",
        "javascript",
        "js name",
        "expression",
        1l,
        2l,
        Instant.now());
  }
}

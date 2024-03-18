package gov.cdc.nbs.questionbank.pagerules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.Arrays;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
class PageRuleUpdaterTest {

  @Mock
  private EntityManager entityManager;
  @Mock
  private ConceptFinder conceptFinder;
  @Mock
  private PageRuleFinder finder;
  @Mock
  private DateCompareCommandCreator dateCompareCreator;
  @Mock
  private EnableDisableCommandCreator enableDisableCreator;
  @Mock
  private HideUnhideCommandCreator hideUnhideCreator;
  @Mock
  private RequireIfCommandCreator requireIfCreator;

  @InjectMocks
  private PageRuleUpdater updater;



  @Test
  void should_fail_invalid_rule() {
    when(entityManager.find(WaRuleMetadata.class, 1l)).thenReturn(null);

    assertThrows(RuleException.class, () -> updater.updatePageRule(1l, null, 3l));
  }

  @Test
  void should_fail_null_page() {
    WaRuleMetadata mockRule = Mockito.mock(WaRuleMetadata.class);
    when(mockRule.getWaTemplateUid()).thenReturn(78l);
    when(entityManager.find(WaRuleMetadata.class, 1l)).thenReturn(mockRule);

    when(entityManager.find(WaTemplate.class, 78l)).thenReturn(null);

    assertThrows(RuleException.class, () -> updater.updatePageRule(1l, null, 3l));
  }

  @Test
  void should_fail_published_page() {
    WaRuleMetadata mockRule = Mockito.mock(WaRuleMetadata.class);
    when(mockRule.getWaTemplateUid()).thenReturn(78l);
    when(entityManager.find(WaRuleMetadata.class, 1l)).thenReturn(mockRule);


    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Published");
    when(entityManager.find(WaTemplate.class, 78l)).thenReturn(template);

    assertThrows(RuleException.class, () -> updater.updatePageRule(1l, null, 3l));
  }

  @Test
  void should_set_source_values() {
    RuleRequest request = new RuleRequest(
        RuleFunction.ENABLE,
        "desc",
        "source",
        true,
        null,
        null,
        null,
        null,
        null,
        null);

    when(conceptFinder.findByQuestionIdentifier("source", 1l)).thenReturn(Arrays.asList(new Concept(
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
    RuleRequest updatedRequest = updater.addSourceValues(request, 1l);
    assertThat(updatedRequest.sourceValues()).isNotEmpty();
  }


  @Test
  void enable() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    when(rule.getWaTemplateUid()).thenReturn(66l);
    when(entityManager.find(WaRuleMetadata.class, 99l)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findById(99l)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66l)).thenReturn(template);

    RuleRequest request = new RuleRequest(
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

    when(enableDisableCreator.update(99l, request, 1l)).thenReturn(command(99l));
    updater.updatePageRule(99l, request, 1l);
    verify(entityManager).flush();
  }

  @Test
  void disable() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    when(rule.getWaTemplateUid()).thenReturn(66l);
    when(entityManager.find(WaRuleMetadata.class, 99l)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findById(99l)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66l)).thenReturn(template);

    RuleRequest request = new RuleRequest(
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
    when(enableDisableCreator.update(99l, request, 1l)).thenReturn(command(99l));

    updater.updatePageRule(99l, request, 1l);
    verify(entityManager).flush();
  }

  @Test
  void date() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    when(rule.getWaTemplateUid()).thenReturn(66l);
    when(entityManager.find(WaRuleMetadata.class, 99l)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findById(99l)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66l)).thenReturn(template);

    RuleRequest request = new RuleRequest(
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
    when(dateCompareCreator.update(99l, request, 1l)).thenReturn(command(99l));

    updater.updatePageRule(99l, request, 1l);
    verify(entityManager).flush();
  }

  @Test
  void hide() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    when(rule.getWaTemplateUid()).thenReturn(66l);
    when(entityManager.find(WaRuleMetadata.class, 99l)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findById(99l)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66l)).thenReturn(template);

    RuleRequest request = new RuleRequest(
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
    when(hideUnhideCreator.update(99l, request, 1l)).thenReturn(command(99l));

    updater.updatePageRule(99l, request, 1l);
    verify(entityManager).flush();
  }

  @Test
  void unhide() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    when(rule.getWaTemplateUid()).thenReturn(66l);
    when(entityManager.find(WaRuleMetadata.class, 99l)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findById(99l)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66l)).thenReturn(template);

    RuleRequest request = new RuleRequest(
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
    when(hideUnhideCreator.update(99l, request, 1l)).thenReturn(command(99l));

    updater.updatePageRule(99l, request, 1l);
    verify(entityManager).flush();
  }

  @Test
  void require_if() {
    WaRuleMetadata rule = Mockito.mock(WaRuleMetadata.class);
    when(rule.getId()).thenReturn(99l);
    when(rule.getWaTemplateUid()).thenReturn(66l);
    when(entityManager.find(WaRuleMetadata.class, 99l)).thenReturn(rule);

    Rule mockRule = Mockito.mock(Rule.class);
    when(finder.findById(99l)).thenReturn(mockRule);

    WaTemplate template = Mockito.mock(WaTemplate.class);
    when(template.getTemplateType()).thenReturn("Draft");
    when(entityManager.find(WaTemplate.class, 66l)).thenReturn(template);

    RuleRequest request = new RuleRequest(
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
    when(requireIfCreator.update(99l, request, 1l)).thenReturn(command(99l));

    updater.updatePageRule(99l, request, 1l);
    verify(entityManager).flush();
  }

  private PageContentCommand.UpdateRuleCommand command(long id) {
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
        1l,
        Instant.now());
  }

}

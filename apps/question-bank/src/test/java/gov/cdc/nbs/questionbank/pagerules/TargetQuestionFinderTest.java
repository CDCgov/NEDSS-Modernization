package gov.cdc.nbs.questionbank.pagerules;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.page.detail.PagesResolver;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSubSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesTab;
import gov.cdc.nbs.questionbank.pagerules.Rule.Target;
import gov.cdc.nbs.questionbank.pagerules.request.TargetQuestionRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TargetQuestionFinderTest {

  @Mock PagesResolver resolver;

  @Mock PageRuleFinder ruleFinder;

  @InjectMocks TargetQuestionFinder targetQuestionFinder;

  @Test
  void testFilterDateQuestions() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterDateQuestionsWithDateTime() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePageWithDateTime());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterDateQuestionsWithoutDataType() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePageWithoutDataType());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterDateQuestionsWithoutStandardNnd() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePageWithoutStandardNnd());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterDateQuestionsWithoutVisible() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePageWithoutVisible());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterDateQuestionsWithoutComponentBehavior() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePageWithoutComponentBehavior());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterDateQuestionsWithoutPage() {
    long pageId = 1L;

    when(resolver.resolve(pageId)).thenReturn(Optional.empty());

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterDateQuestionSameSource() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156348L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterDateQuestionWithDiffSourceWithSameTarget() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);

    Collection<PagesQuestion> targetQuestion = new ArrayList<>();
    targetQuestion.add(
        new PagesQuestion(
            1156348L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null));

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, targetQuestion);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterDateQuestionWithDiffSourceWithDiffTarget() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);

    Collection<PagesQuestion> targetQuestion = new ArrayList<>();
    targetQuestion.add(
        new PagesQuestion(
            2L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null));

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, targetQuestion);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestions() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestionsGroup() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageGroup());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            1,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            "TESTING",
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestionsGroupWithDiffGroupSource() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageGroup());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            "SOMETHINGELSE",
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsGroupWithDiffCompSource() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageGroupWithDiffComp());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            "SOMETHINGELSE",
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsGroupWithSameTarget() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageGroup());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            1,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            "TESTING",
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, getSameTargets());
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestionsGroupWithSameRules() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageGroup());
    List<Rule> rules = getSameRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            1,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            "TESTING",
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsGroupWithSameSource() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageGroup());
    List<Rule> rules = getSameRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            1,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            "TESTING",
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsRequireIf() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.REQUIRE_IF, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestionsRequireIfWithSameTargets() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.REQUIRE_IF, sourceQuestion, getSameTargets());
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestionsRequireIfWithDiffTargets() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.REQUIRE_IF, sourceQuestion, getTargets());
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestionsRequireIfWithDiffTargetsWithSameRule() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getSameRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.REQUIRE_IF, sourceQuestion, getTargets());
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsRequireIfWithoutCompBehavior() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageWithoutCompBehavior());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.REQUIRE_IF, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsRequireIfWithoutRequired() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageWithoutRequired());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.REQUIRE_IF, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithStatic() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageWithStatic());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithoutStandardNnd() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageWithoutStandardNnd());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithoutGroupSeqNum() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageWithoutGroupSeqNum());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithoutPage() {
    long pageId = 1L;

    when(resolver.resolve(pageId)).thenReturn(Optional.empty());

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithUsedTarget() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getSameRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithSameSource() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            0,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithSameSourceSameTargetRule() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getSameRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            0,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithDiffSourceDiffTarget() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    Collection<PagesQuestion> targets = getTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithDiffSourceSameTarget() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    Collection<PagesQuestion> targets = getSameTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterQuestionsWithSameSourceDiffTarget() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    Collection<PagesQuestion> targets = getTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            0,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterQuestionsWithSameSourceSameRuleDiffTarget() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getSameRules();

    Collection<PagesQuestion> targets = getTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            0,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterQuestionsWithDiffSourceSameRuleDiffTarget() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getSameRules();

    Collection<PagesQuestion> targets = getTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(
            1156360L,
            false,
            true,
            "PHIN",
            "INV2002",
            "Reported Age Units",
            21,
            0,
            "IPO",
            "test decript",
            false,
            "CODED",
            null,
            false,
            "test tooll tip",
            true,
            true,
            false,
            null,
            "AGE_UNIT",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_AGE_REPORTED_UNIT",
            "Patient Age Reported Units",
            "PATIENT_AGE_RPTD_UNIT",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");

    TargetQuestionRequest request =
        new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  Collection<PagesQuestion> getSameTargets() {
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            0,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general"));
    return questions;
  }

  Collection<PagesQuestion> getTargets() {
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(
        new PagesQuestion(
            1156348L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null));
    return questions;
  }

  List<Rule> getRules() {
    List<String> sourceValues = new ArrayList<>();
    sourceValues.add("Yes");

    List<Target> targets = new ArrayList<>();
    targets.add(new Target("DEM128", "test label"));

    List<Rule> rules = new ArrayList<>();

    Rule rule =
        new Rule(
            100,
            200L,
            Rule.RuleFunction.ENABLE,
            "testDescription",
            null,
            true,
            sourceValues,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targets);
    rules.add(rule);
    return rules;
  }

  List<Rule> getSameRules() {
    List<String> sourceValues = new ArrayList<>();
    sourceValues.add("Yes");

    List<Target> targets = new ArrayList<>();
    targets.add(new Target("DEM107", "test label"));

    List<Rule> rules = new ArrayList<>();

    Rule rule =
        new Rule(
            100,
            200L,
            Rule.RuleFunction.ENABLE,
            "testDescription",
            null,
            true,
            sourceValues,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targets);
    rules.add(rule);
    return rules;
  }

  PagesResponse getPage() {
    PagesQuestion question =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            0,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getPageGroup() {
    PagesQuestion question =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            1,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            "TESTING",
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getPageGroupWithDiffComp() {
    PagesQuestion question =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            1,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1016,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            "TESTING",
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getPageWithoutCompBehavior() {
    PagesQuestion question =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            0,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            null,
            0,
            false,
            null,
            0,
            "something",
            "Single-Select (Drop down)",
            "code_value_general");
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getPageWithoutRequired() {
    PagesQuestion question =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            0,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            true,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getPageWithStatic() {
    PagesQuestion question =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            0,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            null,
            0,
            false,
            null,
            0,
            "Static",
            "Single-Select (Drop down)",
            "code_value_general");
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getPageWithoutGroupSeqNum() {
    PagesQuestion question =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            1,
            "IPO",
            "The patient's name suffix",
            false,
            "CODED",
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getPageWithoutStandardNnd() {
    PagesQuestion question =
        new PagesQuestion(
            1156355L,
            true,
            true,
            "SYS",
            "DEM107",
            "Suffix",
            15,
            0,
            "IPO",
            "The patient's name suffix",
            false,
            null,
            null,
            false,
            "The patient's name suffix",
            true,
            true,
            false,
            null,
            "P_NM_SFX",
            1007,
            null,
            null,
            "D_PATIENT",
            "PATIENT_NAME_SUFFIX",
            "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false,
            null,
            0,
            false,
            null,
            0,
            "coded_data",
            "Single-Select (Drop down)",
            "code_value_general");
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getDatePage() {
    PagesQuestion question =
        new PagesQuestion(
            1156348L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getDatePageWithoutDataType() {
    PagesQuestion question =
        new PagesQuestion(
            1156348L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            null,
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getDatePageWithDateTime() {
    PagesQuestion question =
        new PagesQuestion(
            1156348L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATETIME",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getDatePageWithoutStandardNnd() {
    PagesQuestion question =
        new PagesQuestion(
            1156348L,
            true,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getDatePageWithoutVisible() {
    PagesQuestion question =
        new PagesQuestion(
            1156348L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            false,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "text_data",
            "User entered text, number, or date",
            null);
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }

  PagesResponse getDatePageWithoutComponentBehavior() {
    PagesQuestion question =
        new PagesQuestion(
            1156348L,
            false,
            true,
            "SYS",
            "NBS104",
            "Information As of Date",
            7,
            0,
            "IPO",
            "As of Date is the last known date for which the information is valid.",
            false,
            "DATE",
            "DATE",
            false,
            "As of Date is the last known date for which the information is valid.",
            true,
            true,
            true,
            null,
            null,
            1008,
            null,
            "10",
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            false,
            null,
            0,
            "soethingelse",
            "User entered text, number, or date",
            null);
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(question);
    PagesSubSection subsection =
        new PagesSubSection(4L, "test subsection", 3, false, false, false, null, null, questions);
    Collection<PagesSubSection> subsections = new ArrayList<>();
    subsections.add(subsection);
    PagesSection section = new PagesSection(3L, "test", 2, false, subsections);
    Collection<PagesSection> sections = new ArrayList<>();
    sections.add(section);
    PagesTab tab = new PagesTab(2L, "testtab", 1, false, sections);
    Collection<PagesTab> tabs = new ArrayList<>();
    tabs.add(tab);
    return new PagesResponse(1L, "test", null, null, 0, tabs, null);
  }
}

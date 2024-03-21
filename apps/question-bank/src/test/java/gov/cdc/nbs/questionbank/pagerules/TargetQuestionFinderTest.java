package gov.cdc.nbs.questionbank.pagerules;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import gov.cdc.nbs.questionbank.page.detail.PagesResolver;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSubSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesTab;
import gov.cdc.nbs.questionbank.pagerules.Rule.Target;
import gov.cdc.nbs.questionbank.pagerules.request.TargetQuestionRequest;

public class TargetQuestionFinderTest {

  @Mock
  PagesResolver resolver;

  @Mock
  PageRuleFinder ruleFinder;

  @InjectMocks
  TargetQuestionFinder targetQuestionFinder;

  public TargetQuestionFinderTest() {
    MockitoAnnotations.openMocks(this);
  }


  @Test
  void testFilterDateQuestions() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion = new PagesQuestion(1L, false, true, "SYS", "NBS104", "Information As of Date", 7, 0,
        "IPO", "As of Date is the last known date for which the information is valid.", false, "DATE", "DATE", false,
        "As of Date is the last known date for which the information is valid.", true, true, true, null, null, 1008,
        null, "10", null, null, null, null, false, null, 0, false, null, 0, "text_data",
        "User entered text, number, or date", null);

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterDateQuestionSameSource() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion = new PagesQuestion(1156348L, false, true, "SYS", "NBS104", "Information As of Date",
        7, 0,
        "IPO", "As of Date is the last known date for which the information is valid.", false, "DATE", "DATE", false,
        "As of Date is the last known date for which the information is valid.", true, true, true, null, null, 1008,
        null, "10", null, null, null, null, false, null, 0, false, null, 0, "text_data",
        "User entered text, number, or date", null);

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterDateQuestionDiffSource() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion = new PagesQuestion(1L, false, true, "SYS", "NBS104", "Information As of Date",
        7, 0,
        "IPO", "As of Date is the last known date for which the information is valid.", false, "DATE", "DATE", false,
        "As of Date is the last known date for which the information is valid.", true, true, true, null, null, 1008,
        null, "10", null, null, null, null, false, null, 0, false, null, 0, "text_data",
        "User entered text, number, or date", null);

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterDateQuestionWithDiffSourceWithSameTarget() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion = new PagesQuestion(1L, false, true, "SYS", "NBS104", "Information As of Date",
        7, 0,
        "IPO", "As of Date is the last known date for which the information is valid.", false, "DATE", "DATE", false,
        "As of Date is the last known date for which the information is valid.", true, true, true, null, null, 1008,
        null, "10", null, null, null, null, false, null, 0, false, null, 0, "text_data",
        "User entered text, number, or date", null);

    Collection<PagesQuestion> targetQuestion = new ArrayList<>();
    targetQuestion.add(new PagesQuestion(1156348L, false, true, "SYS", "NBS104", "Information As of Date", 7, 0,
        "IPO", "As of Date is the last known date for which the information is valid.", false, "DATE", "DATE", false,
        "As of Date is the last known date for which the information is valid.", true, true, true, null, null, 1008,
        null, "10", null, null, null, null, false, null, 0, false, null, 0, "text_data",
        "User entered text, number, or date", null));

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterDateQuestionWithDiffSourceWithDiffTarget() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion = new PagesQuestion(1L, false, true, "SYS", "NBS104", "Information As of Date",
        7, 0,
        "IPO", "As of Date is the last known date for which the information is valid.", false, "DATE", "DATE", false,
        "As of Date is the last known date for which the information is valid.", true, true, true, null, null, 1008,
        null, "10", null, null, null, null, false, null, 0, false, null, 0, "text_data",
        "User entered text, number, or date", null);

    Collection<PagesQuestion> targetQuestion = new ArrayList<>();
    targetQuestion.add(new PagesQuestion(2L, false, true, "SYS", "NBS104", "Information As of Date", 7, 0,
        "IPO", "As of Date is the last known date for which the information is valid.", false, "DATE", "DATE", false,
        "As of Date is the last known date for which the information is valid.", true, true, true, null, null, 1008,
        null, "10", null, null, null, null, false, null, 0, false, null, 0, "text_data",
        "User entered text, number, or date", null));

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterDateQuestionWithSameSourceWithDiffTarget() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());

    when(resolver.resolve(pageId)).thenReturn(page);

    PagesQuestion sourceQuestion = new PagesQuestion(1156348L, false, true, "SYS", "NBS104", "Information As of Date",
        7, 0,
        "IPO", "As of Date is the last known date for which the information is valid.", false, "DATE", "DATE", false,
        "As of Date is the last known date for which the information is valid.", true, true, true, null, null, 1008,
        null, "10", null, null, null, null, false, null, 0, false, null, 0, "text_data",
        "User entered text, number, or date", null);

    Collection<PagesQuestion> targetQuestion = new ArrayList<>();
    targetQuestion.add(new PagesQuestion(1L, false, true, "SYS", "NBS104", "Information As of Date", 7, 0,
        "IPO", "As of Date is the last known date for which the information is valid.", false, "DATE", "DATE", false,
        "As of Date is the last known date for which the information is valid.", true, true, true, null, null, 1008,
        null, "10", null, null, null, null, false, null, 0, false, null, 0, "text_data",
        "User entered text, number, or date", null));

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestions() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156360L, false, true, "PHIN", "INV2002", "Reported Age Units", 21, 0, "IPO", "test decript",
            false, "CODED", null, false, "test tooll tip", true, true, false, null, "AGE_UNIT", 1007, null, null,
            "D_PATIENT", "PATIENT_AGE_REPORTED_UNIT", "Patient Age Reported Units", "PATIENT_AGE_RPTD_UNIT", false,
            null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithUsedTarget() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getSameRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156360L, false, true, "PHIN", "INV2002", "Reported Age Units", 21, 0, "IPO", "test decript",
            false, "CODED", null, false, "test tooll tip", true, true, false, null, "AGE_UNIT", 1007, null, null,
            "D_PATIENT", "PATIENT_AGE_REPORTED_UNIT", "Patient Age Reported Units", "PATIENT_AGE_RPTD_UNIT", false,
            null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithSameSource() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156355L, false, true, "SYS", "DEM107", "Suffix", 15, 0, "IPO",
            "The patient's name suffix", false, "CODED", null, false, "The patient's name suffix", true, true, false,
            null,
            "P_NM_SFX", 1007, null, null, "D_PATIENT", "PATIENT_NAME_SUFFIX", "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false, null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithSameSourceSameTargetRule() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getSameRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156355L, false, true, "SYS", "DEM107", "Suffix", 15, 0, "IPO",
            "The patient's name suffix", false, "CODED", null, false, "The patient's name suffix", true, true, false,
            null,
            "P_NM_SFX", 1007, null, null, "D_PATIENT", "PATIENT_NAME_SUFFIX", "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false, null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithDiffSourceSameTargetRule() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getSameRules();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156360L, false, true, "PHIN", "INV2002", "Reported Age Units", 21, 0, "IPO", "test decript",
            false, "CODED", null, false, "test tooll tip", true, true, false, null, "AGE_UNIT", 1007, null, null,
            "D_PATIENT", "PATIENT_AGE_REPORTED_UNIT", "Patient Age Reported Units", "PATIENT_AGE_RPTD_UNIT", false,
            null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, null);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithDiffSourceDiffTarget() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    Collection<PagesQuestion> targets = getTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156360L, false, true, "PHIN", "INV2002", "Reported Age Units", 21, 0, "IPO", "test decript",
            false, "CODED", null, false, "test tooll tip", true, true, false, null, "AGE_UNIT", 1007, null, null,
            "D_PATIENT", "PATIENT_AGE_REPORTED_UNIT", "Patient Age Reported Units", "PATIENT_AGE_RPTD_UNIT", false,
            null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithDiffSourceSameTarget() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    Collection<PagesQuestion> targets = getSameTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156360L, false, true, "PHIN", "INV2002", "Reported Age Units", 21, 0, "IPO", "test decript",
            false, "CODED", null, false, "test tooll tip", true, true, false, null, "AGE_UNIT", 1007, null, null,
            "D_PATIENT", "PATIENT_AGE_REPORTED_UNIT", "Patient Age Reported Units", "PATIENT_AGE_RPTD_UNIT", false,
            null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterQuestionsWithSameSourceDiffTarget() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    Collection<PagesQuestion> targets = getTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156355L, false, true, "SYS", "DEM107", "Suffix", 15, 0, "IPO",
            "The patient's name suffix", false, "CODED", null, false, "The patient's name suffix", true, true, false,
            null,
            "P_NM_SFX", 1007, null, null, "D_PATIENT", "PATIENT_NAME_SUFFIX", "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false, null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterQuestionsWithSameSourceSameRuleDiffTarget() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getSameRules();

    Collection<PagesQuestion> targets = getTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156355L, false, true, "SYS", "DEM107", "Suffix", 15, 0, "IPO",
            "The patient's name suffix", false, "CODED", null, false, "The patient's name suffix", true, true, false,
            null,
            "P_NM_SFX", 1007, null, null, "D_PATIENT", "PATIENT_NAME_SUFFIX", "Patient Name Suffix",
            "PATIENT_NAME_SUFFIX",
            false, null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterQuestionsWithDiffSourceDiffRuleDiffTarget() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    Collection<PagesQuestion> targets = getTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156360L, false, true, "PHIN", "INV2002", "Reported Age Units", 21, 0, "IPO", "test decript",
            false, "CODED", null, false, "test tooll tip", true, true, false, null, "AGE_UNIT", 1007, null, null,
            "D_PATIENT", "PATIENT_AGE_REPORTED_UNIT", "Patient Age Reported Units", "PATIENT_AGE_RPTD_UNIT", false,
            null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterQuestionsWithDiffSourceSameRuleDiffTarget() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getSameRules();

    Collection<PagesQuestion> targets = getTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156360L, false, true, "PHIN", "INV2002", "Reported Age Units", 21, 0, "IPO", "test decript",
            false, "CODED", null, false, "test tooll tip", true, true, false, null, "AGE_UNIT", 1007, null, null,
            "D_PATIENT", "PATIENT_AGE_REPORTED_UNIT", "Patient Age Reported Units", "PATIENT_AGE_RPTD_UNIT", false,
            null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterQuestionsWithDiffSourceDiffRuleSameTarget() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    List<Rule> rules = getRules();

    Collection<PagesQuestion> targets = getSameTargets();

    when(resolver.resolve(pageId)).thenReturn(page);
    when(ruleFinder.getAllRules(pageId)).thenReturn(rules);

    PagesQuestion sourceQuestion =
        new PagesQuestion(1156360L, false, true, "PHIN", "INV2002", "Reported Age Units", 21, 0, "IPO", "test decript",
            false, "CODED", null, false, "test tooll tip", true, true, false, null, "AGE_UNIT", 1007, null, null,
            "D_PATIENT", "PATIENT_AGE_REPORTED_UNIT", "Patient Age Reported Units", "PATIENT_AGE_RPTD_UNIT", false,
            null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");

    TargetQuestionRequest request = new TargetQuestionRequest(Rule.RuleFunction.ENABLE, sourceQuestion, targets);
    PagesResponse result = targetQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  Collection<PagesQuestion> getSameTargets() {
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(new PagesQuestion(1156355L, false, true, "SYS", "DEM107", "Suffix", 15, 0, "IPO",
        "The patient's name suffix", false, "CODED", null, false, "The patient's name suffix", true, true, false, null,
        "P_NM_SFX", 1007, null, null, "D_PATIENT", "PATIENT_NAME_SUFFIX", "Patient Name Suffix", "PATIENT_NAME_SUFFIX",
        false, null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general"));
    return questions;
  }

  Collection<PagesQuestion> getTargets() {
    Collection<PagesQuestion> questions = new ArrayList<>();
    questions.add(new PagesQuestion(1156348L, false, true, "SYS", "NBS104", "Information As of Date", 7, 0,
        "IPO", "As of Date is the last known date for which the information is valid.", false, "DATE", "DATE", false,
        "As of Date is the last known date for which the information is valid.", true, true, true, null, null, 1008,
        null, "10", null, null, null, null, false, null, 0, false, null, 0, "text_data",
        "User entered text, number, or date", null));
    return questions;
  }

  List<Rule> getRules() {
    List<String> sourceValues = new ArrayList<>();
    sourceValues.add("Yes");

    List<Target> targets = new ArrayList<>();
    targets.add(new Target("DEM128", "test label"));

    List<Rule> rules = new ArrayList<>();

    Rule rule = new Rule(100,
        200l,
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

    Rule rule = new Rule(100,
        200l,
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
    PagesQuestion question = new PagesQuestion(1156355L, false, true, "SYS", "DEM107", "Suffix", 15, 0, "IPO",
        "The patient's name suffix", false, "CODED", null, false, "The patient's name suffix", true, true, false, null,
        "P_NM_SFX", 1007, null, null, "D_PATIENT", "PATIENT_NAME_SUFFIX", "Patient Name Suffix", "PATIENT_NAME_SUFFIX",
        false, null, 0, false, null, 0, "coded_data", "Single-Select (Drop down)", "code_value_general");
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
    PagesResponse page = new PagesResponse(1L, "test", null, null, 0, tabs, null);
    return page;
  }

  PagesResponse getDatePage() {
    PagesQuestion question = new PagesQuestion(1156348L, false, true, "SYS", "NBS104", "Information As of Date", 7, 0,
        "IPO", "As of Date is the last known date for which the information is valid.", false, "DATE", "DATE", false,
        "As of Date is the last known date for which the information is valid.", true, true, true, null, null, 1008,
        null, "10", null, null, null, null, false, null, 0, false, null, 0, "text_data",
        "User entered text, number, or date", null);
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
    PagesResponse page = new PagesResponse(1L, "test", null, null, 0, tabs, null);
    return page;
  }
}

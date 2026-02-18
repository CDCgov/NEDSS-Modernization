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
import gov.cdc.nbs.questionbank.pagerules.request.SourceQuestionRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SourceQuestionFinderTest {

  @Mock PagesResolver resolver;

  @InjectMocks SourceQuestionFinder sourceQuestionFinder;

  @Test
  void testFilterDateQuestions() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());
    when(resolver.resolve(pageId)).thenReturn(page);
    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.DATE_COMPARE);

    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestions() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    when(resolver.resolve(pageId)).thenReturn(page);

    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.ENABLE);
    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithoutPage() {
    long pageId = 1L;
    when(resolver.resolve(pageId)).thenReturn(Optional.empty());

    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.ENABLE);
    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterDateQuestionsWithoutPage() {
    long pageId = 1L;
    when(resolver.resolve(pageId)).thenReturn(Optional.empty());

    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.DATE_COMPARE);
    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithoutDataType() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageNoDataType());
    when(resolver.resolve(pageId)).thenReturn(page);

    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.ENABLE);
    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithDatePage() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());
    when(resolver.resolve(pageId)).thenReturn(page);

    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.ENABLE);
    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilerOtherQuestionsWithTrueStandardNnd() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageWithTrueStandardNnd());
    when(resolver.resolve(pageId)).thenReturn(page);

    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.ENABLE);
    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithoutCompBehavior() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageWithoutCompBehavior());
    when(resolver.resolve(pageId)).thenReturn(page);

    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.ENABLE);
    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterOtherQuestionsWithInv169() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageWithInv169());
    when(resolver.resolve(pageId)).thenReturn(page);

    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.ENABLE);
    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterDateQuestionsWithDateTime() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePageDateTime());
    when(resolver.resolve(pageId)).thenReturn(page);
    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.DATE_COMPARE);

    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterDateQuestionsWithoutDataType() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePageWithoutDataType());
    when(resolver.resolve(pageId)).thenReturn(page);
    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.DATE_COMPARE);

    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterDateQuestionsWithoutStandardNnd() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePageWithoutStandardNnd());
    when(resolver.resolve(pageId)).thenReturn(page);
    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.DATE_COMPARE);

    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
  }

  @Test
  void testFilterDateQuestionWithoutVisible() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePageWithoutVisible());
    when(resolver.resolve(pageId)).thenReturn(page);
    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.DATE_COMPARE);

    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNull(result);
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

  PagesResponse getPageWithInv169() {
    PagesQuestion question =
        new PagesQuestion(
            1156355L,
            false,
            true,
            "SYS",
            "INV169",
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
            "something else");
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
            "something else",
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

  PagesResponse getPageWithTrueStandardNnd() {
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

  PagesResponse getPageNoDataType() {
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

  PagesResponse getDatePageDateTime() {
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

  PagesResponse getDatePageWithoutVisible() {
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
            "DATETIME",
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
}

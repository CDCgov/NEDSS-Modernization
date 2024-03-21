package gov.cdc.nbs.questionbank.pagerules;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
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
import gov.cdc.nbs.questionbank.pagerules.request.SourceQuestionRequest;

class SourceQuestionFinderTest {

  @Mock
  PagesResolver resolver;

  @InjectMocks
  SourceQuestionFinder sourceQuestionFinder;

  public SourceQuestionFinderTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFilterDateQuestions() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getDatePage());
    when(resolver.resolve(pageId)).thenReturn(page);
    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.DATE_COMPARE);

    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
  }

  @Test
  void testFilterOtherQuestions() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());
    when(resolver.resolve(pageId)).thenReturn(page);

    SourceQuestionRequest request = new SourceQuestionRequest(Rule.RuleFunction.ENABLE);
    PagesResponse result = sourceQuestionFinder.filterQuestions(pageId, request);
    assertNotNull(result);
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

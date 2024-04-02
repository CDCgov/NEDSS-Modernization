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
import gov.cdc.nbs.questionbank.pagerules.request.TargetSubsectionRequest;

public class TargetSubsectionFinderTest {
  @Mock
  PagesResolver resolver;

  @Mock
  PageRuleFinder ruleFinder;

  @InjectMocks
  TargetSubsectionFinder targetSubsectionFinder;

  public TargetSubsectionFinderTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFilterSubsections() {
    Long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());

    when(resolver.resolve(pageId)).thenReturn(page);

    TargetSubsectionRequest request = new TargetSubsectionRequest(1, null);
    Collection<PagesSubSection> response = targetSubsectionFinder.filterSubsections(pageId, request);
    assertNotNull(response);
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
}

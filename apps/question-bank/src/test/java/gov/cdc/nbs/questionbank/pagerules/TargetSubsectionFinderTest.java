package gov.cdc.nbs.questionbank.pagerules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.page.detail.PagesResolver;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSubSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesTab;
import gov.cdc.nbs.questionbank.pagerules.Rule.Target;
import gov.cdc.nbs.questionbank.pagerules.request.TargetSubsectionRequest;
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
class TargetSubsectionFinderTest {
  @Mock PagesResolver resolver;

  @Mock PageRuleFinder ruleFinder;

  @InjectMocks TargetSubsectionFinder targetSubsectionFinder;

  @Test
  void testFilterSubsections() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());

    when(resolver.resolve(pageId)).thenReturn(page);

    TargetSubsectionRequest request = new TargetSubsectionRequest(1, null);
    Collection<PagesSubSection> response =
        targetSubsectionFinder.filterSubsections(pageId, request);
    assertEquals(1, response.size());
  }

  @Test
  void testFilterSubsectionsGreaterOrderNumber() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());

    when(resolver.resolve(pageId)).thenReturn(page);

    TargetSubsectionRequest request = new TargetSubsectionRequest(22, null);
    Collection<PagesSubSection> response =
        targetSubsectionFinder.filterSubsections(pageId, request);
    assertEquals(0, response.size());
  }

  @Test
  void testFilterSubsectionsEmptySubsections() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPageWithoutQuestions());

    when(resolver.resolve(pageId)).thenReturn(page);

    TargetSubsectionRequest request = new TargetSubsectionRequest(1, null);
    Collection<PagesSubSection> response =
        targetSubsectionFinder.filterSubsections(pageId, request);
    assertEquals(0, response.size());
  }

  @Test
  void testFilterSubsectionsWithTargetSubsections() {
    long pageId = 1L;
    Optional<PagesResponse> page = Optional.of(getPage());

    when(resolver.resolve(pageId)).thenReturn(page);

    when(ruleFinder.getAllRules(pageId)).thenReturn(getRules());

    Collection<String> targets = new ArrayList<>();
    targets.add("questionIdent");

    TargetSubsectionRequest request = new TargetSubsectionRequest(0, targets);
    Collection<PagesSubSection> response =
        targetSubsectionFinder.filterSubsections(pageId, request);
    assertEquals(1, response.size());
  }

  List<Rule> getRules() {
    List<String> sourceValues = new ArrayList<>();
    sourceValues.add("Yes");

    List<Target> targets = new ArrayList<>();
    targets.add(new Target("questionIdent", "test label"));

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

  PagesResponse getPageWithoutQuestions() {

    PagesSubSection subsection =
        new PagesSubSection(
            4L,
            "test subsection",
            3,
            false,
            false,
            false,
            null,
            null,
            new ArrayList<PagesQuestion>());
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
        new PagesSubSection(
            4L, "test subsection", 3, false, false, false, "questionIdent", null, questions);
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

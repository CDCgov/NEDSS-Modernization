package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.page.detail.PagesResolver;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSubSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesTab;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.request.SourceQuestionRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class SourceQuestionFinder {
  private final PagesResolver pageResolver;

  private static final String COMPONENT_BEHAVIOR_DATA = "_data";

  SourceQuestionFinder(final PagesResolver pageResolver) {
    this.pageResolver = pageResolver;
  }

  public PagesResponse filterQuestions(Long id, SourceQuestionRequest request) {
    return request.ruleFunction() == RuleFunction.DATE_COMPARE
        ? filterDateQuestions(id)
        : filterOtherQuestions(id);
  }

  private void processQuestions(PagesQuestion question, Collection<PagesQuestion> questionsResult) {
    if ("CODED".equals(question.dataType())
        && !question.isStandardNnd()
        && question.componentBehavior().contains(COMPONENT_BEHAVIOR_DATA)
        && ("INV169".equals(question.question())
            || ("CODE_VALUE_GENERAL".equalsIgnoreCase(question.classCode())))) {
      questionsResult.add(question);
    }
  }

  private void processSubsections(
      PagesSubSection subsection, Collection<PagesSubSection> resultSubSections) {
    Collection<PagesQuestion> questionsResult = new ArrayList<>();
    for (PagesQuestion question : subsection.questions()) {
      processQuestions(question, questionsResult);
    }

    PagesSubSection resultSubsection =
        new PagesSubSection(
            subsection.id(),
            subsection.name(),
            subsection.order(),
            subsection.visible(),
            subsection.isGrouped(),
            subsection.isGroupable(),
            subsection.questionIdentifier(),
            subsection.blockName(),
            questionsResult);

    if (!questionsResult.isEmpty()) {
      resultSubSections.add(resultSubsection);
    }
  }

  private void processSections(PagesSection section, Collection<PagesSection> resultSections) {
    List<PagesSubSection> resultSubSections = new ArrayList<>();
    for (PagesSubSection subsection : section.subSections()) {
      processSubsections(subsection, resultSubSections);
    }

    PagesSection resultSection =
        new PagesSection(
            section.id(), section.name(), section.order(), section.visible(), resultSubSections);

    if (!resultSubSections.isEmpty()) {
      resultSections.add(resultSection);
    }
  }

  private void processTabs(PagesTab tab, Collection<PagesTab> resultTabs) {
    List<PagesSection> resultSections = new ArrayList<>();
    for (PagesSection section : tab.sections()) {
      processSections(section, resultSections);
    }

    PagesTab resultTab =
        new PagesTab(tab.id(), tab.name(), tab.order(), tab.visible(), resultSections);

    if (!resultSections.isEmpty()) {
      resultTabs.add(resultTab);
    }
  }

  private PagesResponse filterOtherQuestions(Long id) {
    Optional<PagesResponse> page = pageResolver.resolve(id);

    PagesResponse result = null;

    List<PagesTab> resultTabs = new ArrayList<>();

    if (!page.isEmpty()) {
      for (PagesTab tab : page.get().tabs()) {
        processTabs(tab, resultTabs);
      }

      if (!resultTabs.isEmpty()) {
        result =
            new PagesResponse(
                page.get().id(),
                page.get().name(),
                page.get().status(),
                page.get().description(),
                page.get().root(),
                resultTabs,
                page.get().rules());
      }
    }

    return result;
  }

  private void processDateQuestions(
      PagesQuestion question, Collection<PagesQuestion> questionsResult) {
    if (("DATE".equals(question.dataType()) || "DATETIME".equals(question.dataType()))
        && !question.isStandardNnd()
        && question.visible()
        && question.componentBehavior().contains(COMPONENT_BEHAVIOR_DATA)) {
      questionsResult.add(question);
    }
  }

  private void processDateSubsections(
      PagesSubSection subsection, Collection<PagesSubSection> resultSubSections) {
    Collection<PagesQuestion> questionsResult = new ArrayList<>();
    for (PagesQuestion question : subsection.questions()) {
      processDateQuestions(question, questionsResult);
    }

    PagesSubSection resultSubsection =
        new PagesSubSection(
            subsection.id(),
            subsection.name(),
            subsection.order(),
            subsection.visible(),
            subsection.isGrouped(),
            subsection.isGroupable(),
            subsection.questionIdentifier(),
            subsection.blockName(),
            questionsResult);

    if (!questionsResult.isEmpty()) {
      resultSubSections.add(resultSubsection);
    }
  }

  private void processDateSections(PagesSection section, Collection<PagesSection> resultSections) {
    List<PagesSubSection> resultSubSections = new ArrayList<>();
    for (PagesSubSection subsection : section.subSections()) {
      processDateSubsections(subsection, resultSubSections);
    }

    PagesSection resultSection =
        new PagesSection(
            section.id(), section.name(), section.order(), section.visible(), resultSubSections);

    if (!resultSubSections.isEmpty()) {
      resultSections.add(resultSection);
    }
  }

  private void processDateTabs(PagesTab tab, Collection<PagesTab> resultTabs) {
    List<PagesSection> resultSections = new ArrayList<>();
    for (PagesSection section : tab.sections()) {
      processDateSections(section, resultSections);
    }

    PagesTab resultTab =
        new PagesTab(tab.id(), tab.name(), tab.order(), tab.visible(), resultSections);

    if (!resultSections.isEmpty()) {
      resultTabs.add(resultTab);
    }
  }

  private PagesResponse filterDateQuestions(Long id) {
    Optional<PagesResponse> page = pageResolver.resolve(id);

    PagesResponse result = null;

    List<PagesTab> resultTabs = new ArrayList<>();

    if (!page.isEmpty()) {
      for (PagesTab tab : page.get().tabs()) {
        processDateTabs(tab, resultTabs);
      }

      if (!resultTabs.isEmpty()) {
        result =
            new PagesResponse(
                page.get().id(),
                page.get().name(),
                page.get().status(),
                page.get().description(),
                page.get().root(),
                resultTabs,
                page.get().rules());
      }
    }

    return result;
  }
}

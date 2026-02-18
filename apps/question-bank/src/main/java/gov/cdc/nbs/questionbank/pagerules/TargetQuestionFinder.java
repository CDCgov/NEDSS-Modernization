package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.page.detail.PagesResolver;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSubSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesTab;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.Target;
import gov.cdc.nbs.questionbank.pagerules.request.TargetQuestionRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TargetQuestionFinder {

  private final PagesResolver pageResolver;
  private final PageRuleFinder ruleFinder;

  private static final String COMPONENT_BEHAVIOR_DATA = "_data";

  TargetQuestionFinder(final PagesResolver pageResolver, final PageRuleFinder ruleFinder) {
    this.pageResolver = pageResolver;
    this.ruleFinder = ruleFinder;
  }

  public PagesResponse filterQuestions(Long id, TargetQuestionRequest request) {
    return request.ruleFunction() == RuleFunction.DATE_COMPARE
        ? filterDateQuestions(id, request)
        : filterOtherQuestions(id, request);
  }

  private void processDateQuestions(
      Collection<PagesQuestion> questionsResult,
      PagesQuestion question,
      TargetQuestionRequest request) {
    List<Long> selectedTargetIds = currentTargetQuestions(request);

    if (("DATE".equals(question.dataType()) || "DATETIME".equals(question.dataType()))
        && !question.isStandardNnd()
        && question.visible()
        && question.componentBehavior().contains(COMPONENT_BEHAVIOR_DATA)) {
      if (request.targetQuestion() != null) {
        if (selectedTargetIds.contains(question.id())
            || question.id() != request.sourceQuestion().id()) {
          questionsResult.add(question);
        }
      } else if (question.id() != request.sourceQuestion().id()) {
        questionsResult.add(question);
      }
    }
  }

  private void processDateSubsections(
      PagesSubSection subsection,
      TargetQuestionRequest request,
      Collection<PagesSubSection> resultSubSections) {
    Collection<PagesQuestion> questionsResult = new ArrayList<>();
    for (PagesQuestion question : subsection.questions()) {
      processDateQuestions(questionsResult, question, request);
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

  private void processDateSections(
      TargetQuestionRequest request,
      PagesSection section,
      Collection<PagesSection> resultSections) {
    List<PagesSubSection> resultSubSections = new ArrayList<>();
    for (PagesSubSection subsection : section.subSections()) {
      processDateSubsections(subsection, request, resultSubSections);
    }

    PagesSection resultSection =
        new PagesSection(
            section.id(), section.name(), section.order(), section.visible(), resultSubSections);

    if (!resultSubSections.isEmpty()) {
      resultSections.add(resultSection);
    }
  }

  private void processDateTabs(
      PagesTab tab, Collection<PagesTab> resultTabs, TargetQuestionRequest request) {
    List<PagesSection> resultSections = new ArrayList<>();
    for (PagesSection section : tab.sections()) {
      processDateSections(request, section, resultSections);
    }

    PagesTab resultTab =
        new PagesTab(tab.id(), tab.name(), tab.order(), tab.visible(), resultSections);

    if (!resultSections.isEmpty()) {
      resultTabs.add(resultTab);
    }
  }

  private PagesResponse filterDateQuestions(Long id, TargetQuestionRequest request) {
    Optional<PagesResponse> page = pageResolver.resolve(id);

    PagesResponse result = null;

    List<PagesTab> resultTabs = new ArrayList<>();

    if (!page.isEmpty()) {
      for (PagesTab tab : page.get().tabs()) {
        processDateTabs(tab, resultTabs, request);
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

  private List<String> previousTargetQuestions(Long id) {
    List<Rule> rules = ruleFinder.getAllRules(id);
    List<String> targetIdentifiers = new ArrayList<>();

    for (Rule rule : rules) {
      for (Target target : rule.targets()) {
        targetIdentifiers.add(target.targetIdentifier());
      }
    }

    return targetIdentifiers;
  }

  private List<Long> currentTargetQuestions(TargetQuestionRequest request) {
    List<Long> result = new ArrayList<>();

    if (request.targetQuestion() != null) {
      for (PagesQuestion question : request.targetQuestion()) {
        result.add(question.id());
      }
    }

    return result;
  }

  private void ifBlock(
      TargetQuestionRequest request,
      List<Long> selectedTargetIds,
      PagesQuestion question,
      List<String> targetIdentifiers,
      Collection<PagesQuestion> questionsResult) {
    if (question.questionGroupSeq() == request.sourceQuestion().questionGroupSeq()
        && question.displayComponent() != 1016L) {
      if (request.targetQuestion() != null) {
        if (selectedTargetIds.contains(question.id())
            || (question.id() != request.sourceQuestion().id()
                && !targetIdentifiers.contains(question.question()))) {
          questionsResult.add(question);
        }
      } else if (question.id() != request.sourceQuestion().id()
          && !targetIdentifiers.contains(question.question())) {
        questionsResult.add(question);
      }
    }
  }

  private void ifRequireIf(
      TargetQuestionRequest request,
      List<Long> selectedTargetIds,
      PagesQuestion question,
      List<String> targetIdentifiers,
      Collection<PagesQuestion> questionsResult) {
    if (!question.required() && question.componentBehavior().contains(COMPONENT_BEHAVIOR_DATA)) {
      if (request.targetQuestion() != null) {
        if (selectedTargetIds.contains(question.id())
            || (question.id() != request.sourceQuestion().id()
                && !targetIdentifiers.contains(question.question()))) {
          questionsResult.add(question);
        }
      } else if (question.id() != request.sourceQuestion().id()) {
        questionsResult.add(question);
      }
    }
  }

  private void ifNotRequireIf(
      TargetQuestionRequest request,
      List<Long> selectedTargetIds,
      PagesQuestion question,
      List<String> targetIdentifiers,
      Collection<PagesQuestion> questionsResult) {
    if ((question.componentBehavior().contains("Static")
        || question.componentBehavior().contains(COMPONENT_BEHAVIOR_DATA))) {
      if (request.targetQuestion() != null) {
        if (selectedTargetIds.contains(question.id())
            || (question.id() != request.sourceQuestion().id()
                && !targetIdentifiers.contains(question.question()))) {
          questionsResult.add(question);
        }
      } else if (question.id() != request.sourceQuestion().id()
          && !targetIdentifiers.contains(question.question())) {
        questionsResult.add(question);
      }
    }
  }

  private void processQuestions(
      TargetQuestionRequest request,
      PagesQuestion question,
      Collection<PagesQuestion> questionsResult,
      Long id) {
    List<Long> selectedTargetIds = currentTargetQuestions(request);
    List<String> targetIdentifiers = previousTargetQuestions(id);

    if (request.sourceQuestion().blockName() != null) {
      ifBlock(request, selectedTargetIds, question, targetIdentifiers, questionsResult);
    } else {
      if (!question.isStandardNnd() && question.questionGroupSeq() == 0) {
        if (request.ruleFunction() == RuleFunction.REQUIRE_IF) {
          ifRequireIf(request, selectedTargetIds, question, targetIdentifiers, questionsResult);
        } else {
          ifNotRequireIf(request, selectedTargetIds, question, targetIdentifiers, questionsResult);
        }
      }
    }
  }

  private void procesSubsections(
      PagesSubSection subsection,
      Long id,
      Collection<PagesSubSection> resultSubSections,
      TargetQuestionRequest request) {
    Collection<PagesQuestion> questionsResult = new ArrayList<>();
    for (PagesQuestion question : subsection.questions()) {
      processQuestions(request, question, questionsResult, id);
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

  private void processSections(
      PagesSection section,
      TargetQuestionRequest request,
      Long id,
      Collection<PagesSection> resultSections) {
    List<PagesSubSection> resultSubSections = new ArrayList<>();
    for (PagesSubSection subsection : section.subSections()) {
      procesSubsections(subsection, id, resultSubSections, request);
    }

    PagesSection resultSection =
        new PagesSection(
            section.id(), section.name(), section.order(), section.visible(), resultSubSections);

    if (!resultSubSections.isEmpty()) {
      resultSections.add(resultSection);
    }
  }

  private void processTabs(
      PagesTab tab, TargetQuestionRequest request, Long id, Collection<PagesTab> resultTabs) {
    List<PagesSection> resultSections = new ArrayList<>();
    for (PagesSection section : tab.sections()) {
      processSections(section, request, id, resultSections);
    }

    PagesTab resultTab =
        new PagesTab(tab.id(), tab.name(), tab.order(), tab.visible(), resultSections);

    if (!resultSections.isEmpty()) {
      resultTabs.add(resultTab);
    }
  }

  private PagesResponse filterOtherQuestions(Long id, TargetQuestionRequest request) {
    Optional<PagesResponse> page = pageResolver.resolve(id);

    PagesResponse result = null;

    List<PagesTab> resultTabs = new ArrayList<>();

    if (!page.isEmpty()) {
      for (PagesTab tab : page.get().tabs()) {
        processTabs(tab, request, id, resultTabs);
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

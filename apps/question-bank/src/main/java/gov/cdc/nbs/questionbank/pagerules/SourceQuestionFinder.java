package gov.cdc.nbs.questionbank.pagerules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.page.detail.PagesResolver;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSubSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesTab;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.request.SourceQuestionRequest;

@Component
public class SourceQuestionFinder {
  private final PagesResolver pageResolver;

  SourceQuestionFinder(final PagesResolver pageResolver) {
    this.pageResolver = pageResolver;
  }

  public PagesResponse filterQuestions(Long id, SourceQuestionRequest request) {
    return request.ruleFunction() == RuleFunction.DATE_COMPARE ? filterDateQuestions(id)
        : filterOtherQuestions(id);
  }

  private PagesResponse filterOtherQuestions(Long id) {
    Optional<PagesResponse> page = pageResolver.resolve(id);

    PagesResponse result = null;

    List<PagesTab> resultTabs = new ArrayList<>();

    if (!page.isEmpty()) {
      for (PagesTab tab : page.get().tabs()) {
        List<PagesSection> resultSections = new ArrayList<>();
        for (PagesSection section : tab.sections()) {

          List<PagesSubSection> resultSubSections = new ArrayList<>();
          for (PagesSubSection subsection : section.subSections()) {

            Collection<PagesQuestion> questionsResult = new ArrayList<>();
            for (PagesQuestion question : subsection.questions()) {
              if (question.dataType() != null && question.dataType().equals("CODED") && !question.isStandardNnd()
                  && question.componentBehavior().contains("_data") && (question.question().equals("INV169")
                      || (question.classCode().equalsIgnoreCase("CODE_VALUE_GENERAL")))) {
                questionsResult.add(question);
              }
            }

            PagesSubSection resultSubsection = new PagesSubSection(subsection.id(), subsection.name(),
                subsection.order(),
                subsection.visible(), subsection.isGrouped(), subsection.isGroupable(), subsection.questionIdentifier(),
                subsection.blockName(), questionsResult);

            if (questionsResult.isEmpty()) {
              resultSubSections.add(resultSubsection);
            }
          }

          PagesSection resultSection =
              new PagesSection(section.id(), section.name(), section.order(), section.visible(), resultSubSections);

          if (resultSubSections.isEmpty()) {
            resultSections.add(resultSection);
          }

        }

        PagesTab resultTab = new PagesTab(tab.id(), tab.name(), tab.order(), tab.visible(), resultSections);

        if (resultSections.isEmpty()) {
          resultTabs.add(resultTab);
        }
      }

      if (resultTabs.isEmpty()) {
        result = new PagesResponse(page.get().id(), page.get().name(), page.get().status(),
            page.get().description(), page.get().root(), resultTabs, page.get().rules());
      }
    }

    return result;

  }

  private PagesResponse filterDateQuestions(Long id) {
    Optional<PagesResponse> page = pageResolver.resolve(id);

    PagesResponse result = null;

    List<PagesTab> resultTabs = new ArrayList<>();

    if (!page.isEmpty()) {
      for (PagesTab tab : page.get().tabs()) {
        List<PagesSection> resultSections = new ArrayList<>();
        for (PagesSection section : tab.sections()) {

          List<PagesSubSection> resultSubSections = new ArrayList<>();
          for (PagesSubSection subsection : section.subSections()) {

            Collection<PagesQuestion> questionsResult = new ArrayList<>();
            for (PagesQuestion question : subsection.questions()) {
              if (question.dataType() != null && (question.dataType().equals("DATE")
                  || question.dataType().equals("DATETIME")) && !question.isStandardNnd() && question.visible()
                  && question.componentBehavior().contains("_data")) {
                questionsResult.add(question);
              }
            }

            PagesSubSection resultSubsection = new PagesSubSection(subsection.id(), subsection.name(),
                subsection.order(),
                subsection.visible(), subsection.isGrouped(), subsection.isGroupable(), subsection.questionIdentifier(),
                subsection.blockName(), questionsResult);

            if (questionsResult.isEmpty()) {
              resultSubSections.add(resultSubsection);
            }
          }

          PagesSection resultSection =
              new PagesSection(section.id(), section.name(), section.order(), section.visible(), resultSubSections);

          if (resultSubSections.isEmpty()) {
            resultSections.add(resultSection);
          }

        }

        PagesTab resultTab = new PagesTab(tab.id(), tab.name(), tab.order(), tab.visible(), resultSections);

        if (resultSections.isEmpty()) {
          resultTabs.add(resultTab);
        }
      }

      if (resultTabs.isEmpty()) {
        result = new PagesResponse(page.get().id(), page.get().name(), page.get().status(),
            page.get().description(), page.get().root(), resultTabs, page.get().rules());
      }
    }

    return result;
  }
}

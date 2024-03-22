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
import gov.cdc.nbs.questionbank.pagerules.Rule.Target;
import gov.cdc.nbs.questionbank.pagerules.request.TargetQuestionRequest;

@Component
public class TargetQuestionFinder {

  private final PagesResolver pageResolver;
  private final PageRuleFinder ruleFinder;

  TargetQuestionFinder(final PagesResolver pageResolver, final PageRuleFinder ruleFinder) {
    this.pageResolver = pageResolver;
    this.ruleFinder = ruleFinder;
  }

  public PagesResponse filterQuestions(Long id, TargetQuestionRequest request) {
    return request.ruleFunction() == RuleFunction.DATE_COMPARE ? filterDateQuestions(id, request)
        : filterOtherQuestions(id, request);
  }

  private PagesResponse filterDateQuestions(Long id, TargetQuestionRequest request) {
    Optional<PagesResponse> page = pageResolver.resolve(id);

    PagesResponse result = null;

    List<Long> selectedTargetIds = currentTargetQuestions(request);

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
                  || question.dataType().equals("DATETIME"))
                  && !question.isStandardNnd() && question.visible()
                  && question.componentBehavior().contains("_data")) {
                if (request.targetQuestion() != null) {
                  if (selectedTargetIds.contains(question.id())) {
                    questionsResult.add(question);
                  } else if (question.id() != request.sourceQuestion().id()) {
                    questionsResult.add(question);
                  }
                } else if (question.id() != request.sourceQuestion().id()) {
                  questionsResult.add(question);
                }
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

  private PagesResponse filterOtherQuestions(Long id, TargetQuestionRequest request) {
    Optional<PagesResponse> page = pageResolver.resolve(id);

    List<String> targetIdentifiers = previousTargetQuestions(id);

    List<Long> selectedTargetIds = currentTargetQuestions(request);

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

              if (request.sourceQuestion().blockName() != null) {
                if (question.questionGroupSeq() == request.sourceQuestion().questionGroupSeq()
                    && question.displayComponent() != 1016L) {
                  if (request.targetQuestion() != null) {
                    if (selectedTargetIds.contains(question.id())) {
                      questionsResult.add(question);
                    } else if (question.id() != request.sourceQuestion().id()
                        && !targetIdentifiers.contains(question.question())) {
                      questionsResult.add(question);
                    }
                  } else if (question.id() != request.sourceQuestion().id()
                      && !targetIdentifiers.contains(question.question())) {
                    questionsResult.add(question);
                  }
                }
              } else {
                if (!question.isStandardNnd() && question.questionGroupSeq() == 0) {
                  if (request.ruleFunction() == RuleFunction.REQUIRE_IF) {
                    if (!question.required() && question.componentBehavior().contains("_data")) {
                      if (request.targetQuestion() != null) {
                        if (selectedTargetIds.contains(question.id())) {
                          questionsResult.add(question);
                        } else if (question.id() != request.sourceQuestion().id()
                            && !targetIdentifiers.contains(question.question())) {
                          questionsResult.add(question);
                        }
                      } else if (question.id() != request.sourceQuestion().id()) {
                        questionsResult.add(question);
                      }
                    }
                  } else {
                    if ((question.componentBehavior().contains("Static")
                        || question.componentBehavior().contains("_data"))) {
                      if (request.targetQuestion() != null) {
                        if (selectedTargetIds.contains(question.id())) {
                          questionsResult.add(question);
                        } else if (question.id() != request.sourceQuestion().id()
                            && !targetIdentifiers.contains(question.question())) {
                          questionsResult.add(question);
                        }
                      } else if (question.id() != request.sourceQuestion().id()
                          && !targetIdentifiers.contains(question.question())) {
                        questionsResult.add(question);
                      }
                    }
                  }
                }
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

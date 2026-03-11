package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.page.detail.PagesResolver;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSubSection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesTab;
import gov.cdc.nbs.questionbank.pagerules.Rule.Target;
import gov.cdc.nbs.questionbank.pagerules.request.TargetSubsectionRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TargetSubsectionFinder {

  private final PagesResolver pageResolver;
  private final PageRuleFinder ruleFinder;

  TargetSubsectionFinder(final PagesResolver pageResolver, final PageRuleFinder ruleFinder) {
    this.pageResolver = pageResolver;
    this.ruleFinder = ruleFinder;
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

  private void processSubsections(
      PagesSection section,
      TargetSubsectionRequest request,
      Long id,
      Collection<PagesSubSection> result) {
    List<String> targetIdentifiers = previousTargetQuestions(id);

    for (PagesSubSection subsection : section.subSections()) {
      if (!subsection.questions().isEmpty()
          && request.orderNbr() <= subsection.order()
          && (!targetIdentifiers.contains(subsection.questionIdentifier())
              || request.targetSubsections().contains(subsection.questionIdentifier()))) {
        result.add(subsection);
      }
    }
  }

  public Collection<PagesSubSection> filterSubsections(Long id, TargetSubsectionRequest request) {
    Collection<PagesSubSection> result = new ArrayList<>();
    Optional<PagesResponse> page = pageResolver.resolve(id);

    if (!page.isEmpty()) {
      for (PagesTab tab : page.get().tabs()) {
        for (PagesSection section : tab.sections()) {
          processSubsections(section, request, id, result);
        }
      }
    }

    return result;
  }
}

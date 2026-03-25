package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.tree.ComponentTreeResolver;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PagesResolver {

  private final PageDescriptionFinder pageFinder;
  private final RuleFinder ruleFinder;
  private final ComponentTreeResolver resolver;
  private final PagesResponseMapper mapper;

  PagesResolver(
      final PageDescriptionFinder pageFinder,
      final RuleFinder ruleFinder,
      final ComponentTreeResolver resolver,
      final PagesResponseMapper mapper) {
    this.pageFinder = pageFinder;
    this.ruleFinder = ruleFinder;
    this.resolver = resolver;
    this.mapper = mapper;
  }

  public Optional<PagesResponse> resolve(final long page) {
    return this.pageFinder.find(page).map(this::withTree);
  }

  private PagesResponse withTree(final PageDescription detailed) {
    Collection<PagesRule> rules = this.ruleFinder.find(detailed.identifier());
    return this.resolver
        .resolve(detailed.identifier())
        .map(tree -> this.mapper.asResponse(detailed, rules, tree))
        .orElseGet(() -> this.mapper.asResponse(detailed, rules));
  }
}

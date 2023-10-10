package gov.cdc.nbs.questionbank.page.detail;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
class DetailedPageResolver {

  private final DetailedPageFinder pageFinder;
  private final DetailedPageRuleFinder ruleFinder;
  private final ComponentTreeResolver resolver;
  private final DetailedPageResponseMapper mapper;

  DetailedPageResolver(
      final DetailedPageFinder pageFinder,
      final DetailedPageRuleFinder ruleFinder,
      final ComponentTreeResolver resolver,
      final DetailedPageResponseMapper mapper
  ) {
    this.pageFinder = pageFinder;
    this.ruleFinder = ruleFinder;
    this.resolver = resolver;
    this.mapper = mapper;
  }

  Optional<DetailedPageResponse> resolve(final long page) {
    return this.pageFinder.find(page).map(this::withTree);
  }

  private DetailedPageResponse withTree(final DetailedPage detailed) {
    Collection<DetailedPageRule> rules = this.ruleFinder.find(detailed.identifier());
    return this.resolver.resolve(detailed.identifier())
        .map(tree -> this.mapper.asResponse(detailed, rules, tree))
        .orElseGet(() -> this.mapper.asResponse(detailed, rules));


  }

}

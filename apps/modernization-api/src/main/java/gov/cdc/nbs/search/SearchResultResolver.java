package gov.cdc.nbs.search;

import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class SearchResultResolver {

  public <R> SearchResult<R> resolve(
      final List<R> results, final Pageable pageable, final long total) {
    return new SimpleSearchResult<>(
        results, (int) total, pageable.getPageNumber(), pageable.getPageSize());
  }

  public <R> SearchResult<R> empty(final Pageable pageable) {
    return new SimpleSearchResult<>(
        Collections.emptyList(), 0, pageable.getPageNumber(), pageable.getPageSize());
  }
}

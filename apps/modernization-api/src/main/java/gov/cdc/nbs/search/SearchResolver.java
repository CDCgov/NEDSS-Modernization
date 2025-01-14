package gov.cdc.nbs.search;

import org.springframework.data.domain.Pageable;

public interface SearchResolver<C, R> {

  SearchResult<R> search(final C criteria, final Pageable pageable);

}

package gov.cdc.nbs.search;

import java.util.List;

public record SimpleSearchResult<R>(
    List<R> content,
    int total,
    int page,
    int size
) implements SearchResult<R> {
}

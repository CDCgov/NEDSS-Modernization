package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import graphql.com.google.common.collect.Ordering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientSearcher {

  private final PatientSearchQueryFilterResolver filterResolver;
  private final PatientSearchQueryResolver queryResolver;
  private final PatientSearchSortResolver sortResolver;
  private final ElasticsearchOperations operations;
  private final PatientSearchResultFinder finder;

  PatientSearcher(
      final PatientSearchQueryFilterResolver filterResolver,
      final PatientSearchQueryResolver queryResolver,
      final PatientSearchSortResolver sortResolver,
      final ElasticsearchOperations operations,
      final PatientSearchResultFinder finder
  ) {
    this.filterResolver = filterResolver;
    this.queryResolver = queryResolver;
    this.sortResolver = sortResolver;
    this.operations = operations;
    this.finder = finder;
  }

  Page<PatientSearchResult> search(
      final PatientFilter criteria,
      final Pageable pageable
  ) {

    NativeSearchQuery query = new NativeSearchQueryBuilder()
        .withFilter(this.filterResolver.resolve(criteria))
        .withQuery(this.queryResolver.resolve(criteria))
        .withSorts(this.sortResolver.resolve(pageable))
        .withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
        .build();

    SearchHits<ElasticsearchPerson> elasticsearchPersonSearchHits = operations.search(
        query,
        ElasticsearchPerson.class
    );

    List<Long> ids = elasticsearchPersonSearchHits
        .stream()
        .map(SearchHit::getContent)
        .map(ElasticsearchPerson::getPersonUid)
        .toList();

    List<PatientSearchResult> results = finder.find(ids)
        .stream()
        .sorted(Ordering.explicit(ids).onResultOf(PatientSearchResult::patient))
        .toList();

    return new PageImpl<>(
        results,
        pageable,
        elasticsearchPersonSearchHits.getTotalHits()
    );
  }

}

package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.event.search.LabReportFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;


@Component
public class LabReportFinder {
  private static final Permission PERMISSION = new Permission("View", "ObservationLabReport");
  private final PermissionScopeResolver resolver;
  private final ElasticsearchOperations operations;
  private final LabReportQueryBuilder queryBuilder;


  public LabReportFinder(
      final PermissionScopeResolver resolver,
      final ElasticsearchOperations operations,
      final LabReportQueryBuilder queryBuilder
  ) {
    this.resolver = resolver;
    this.operations = operations;
    this.queryBuilder = queryBuilder;
  }

  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-OBSERVATIONLABREPORT')")
  public Page<LabReport> find(LabReportFilter filter, Pageable pageable) {
    PermissionScope scope = this.resolver.resolve(PERMISSION);
    var query = queryBuilder.buildLabReportQuery(scope, filter, pageable);
    return performSearch(query, LabReport.class);
  }

  private <T> Page<T> performSearch(NativeSearchQuery query, Class<T> clazz) {
    var hits = operations.search(query, clazz);
    var list = hits.getSearchHits().stream().map(SearchHit::getContent).toList();
    return new PageImpl<>(list, query.getPageable(), hits.getTotalHits());
  }
}

package gov.cdc.nbs.questionbank.page.summary.search;

import com.google.common.collect.Ordering;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.accumulation.Accumulator;
import gov.cdc.nbs.questionbank.filter.querydsl.QueryDSLFilterApplier;
import gov.cdc.nbs.questionbank.order.QueryDSLOrderResolver;
import gov.cdc.nbs.questionbank.page.services.PageSummaryQuery;
import gov.cdc.nbs.questionbank.page.summary.PageSummaryMapper;
import gov.cdc.nbs.questionbank.page.summary.PageSummaryMerger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Component
class PageSummarySearcher {

  private final JPAQueryFactory factory;
  private final PageSummaryTables tables;
  private final PageSummaryQuery query;
  private final PageSummaryMapper mapper;
  private final PageSummaryMerger merger;

  PageSummarySearcher(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new PageSummaryTables();
    this.query = new PageSummaryQuery(this.tables);
    this.mapper = new PageSummaryMapper(this.tables);
    this.merger = new PageSummaryMerger();
  }

  Page<PageSummary> find(final PageSummaryCriteria criteria, final Pageable pageable) {

    // searches on page name and condition
    OrderSpecifier<?>[] ordering = resolveOrdering(pageable);

    List<Long> found = findApplicableSummaries(criteria)
        .orderBy(ordering)
        .stream()
        .map(tuple -> tuple.get(this.tables.page().id))
        .distinct()
        .toList();

    int total = found.size();

    List<Long> ids = found
        .subList((int) pageable.getOffset(),
            Math.min(
                total,
                (int) pageable.getOffset() + pageable.getPageSize()
            )
        );

    return total > 0
        ? resolvePage(ids, ordering, pageable, total)
        : noResultsFound(pageable);
  }

  private Page<PageSummary> noResultsFound(final Pageable pageable) {
    return new PageImpl<>(List.of(), pageable, 0);
  }

  private JPAQuery<Tuple> findApplicableSummaries(final PageSummaryCriteria criteria) {
    return this.factory.selectDistinct(
            this.tables.page().id,
            this.tables.page().templateType,
            this.tables.page().publishVersionNbr,
            this.tables.page().templateNm,
            this.tables.eventType().codeShortDescTxt,
            this.tables.condition().conditionShortNm,
            this.tables.page().lastChgTime,
            this.tables.lastUpdatedBy()
        )
        .from(this.tables.page())
        .join(this.tables.eventType()).on(
            this.tables.eventType().id.codeSetNm.eq("BUS_OBJ_TYPE"),
            this.tables.eventType().id.code.eq(this.tables.page().busObjType)
        )
        .leftJoin(this.tables.authUser())
        .on(this.tables.page().lastChgUserId.eq(this.tables.authUser().nedssEntryId))
        .leftJoin(this.tables.conditionMapping())
        .on(this.tables.page().id.eq(this.tables.conditionMapping().waTemplateUid.id))
        .leftJoin(this.tables.condition())
        .on(this.tables.conditionMapping().conditionCd.eq(this.tables.condition().id))
        .leftJoin(this.tables.mappingGuide()).on(
            this.tables.mappingGuide().id.codeSetNm.eq("NBS_MSG_PROFILE"),
            this.tables.mappingGuide().id.code.eq(this.tables.page().nndEntityIdentifier)
        )
        .where(applyCriteria(criteria));
  }

  private BooleanExpression applyCriteria(final PageSummaryCriteria criteria) {
    // do not load legacy, template, or 'Published with Draft' pages
    // see legacy code PageManagementDAOImpl.java#2485
    BooleanExpression onlyPages = this.tables.page().templateType.in("Draft", "Published");

    return Stream.concat(
            applySearch(criteria),
            QueryDSLFilterApplier.apply(
                this::resolveProperty,
                criteria.filters()
            )
        )
        .reduce(
            onlyPages,
            BooleanExpression::and,
            BooleanExpression::and
        );
  }

  private Stream<BooleanExpression> applySearch(final PageSummaryCriteria criteria) {
    if (criteria.search() != null && !criteria.search().isBlank()) {
      // query template name and condition name
      return Stream.of(this.tables.page().templateNm.contains(criteria.search())
          .or(
              this.tables.condition().conditionShortNm.contains(criteria.search())
                  .or(this.tables.page().id.like(criteria.search()))
          )
      );
    }
    return Stream.empty();
  }

  private Stream<Expression<?>> resolveProperty(final String property) {
    return switch (property.toLowerCase()) {
      case "name" -> Stream.of(this.tables.page().templateNm);
      case "eventtype", "event-type" -> Stream.of(this.tables.eventType().codeShortDescTxt);
      case "condition", "conditions" -> Stream.of(this.tables.condition().conditionShortNm);
      case "status" -> Stream.of(this.tables.page().templateType, this.tables.page().publishVersionNbr);
      case "lastupdatedby" -> Stream.of(this.tables.lastUpdatedBy());
      case "lastupdate" -> Stream.of(this.tables.page().lastChgTime);
      default -> Stream.empty();
    };

  }

  /**
   * Adds order by clauses to query. First order by is the user supplied clause, second is the identifier to prevent
   * non-unique sort exception. Supported fields are: id, name, eventType, status, lastUpdate, lastUpdatedBy
   */
  private OrderSpecifier<?>[] resolveOrdering(final Pageable pageable) {
    return Stream.concat(
            QueryDSLOrderResolver.resolve(
                this::resolveProperty,
                pageable
            ),
            Stream.of(this.tables.page().id.desc())
        ).filter(Objects::nonNull)
        .toArray(OrderSpecifier[]::new);
  }

  /**
   * Retrieves PageSummary objects for a list of Ids
   */
  private Page<PageSummary> resolvePage(
      final List<Long> ids,
      final OrderSpecifier<?>[] ordering,
      final Pageable pageable,
      int totalSize
  ) {
    // get the summaries based on supplied Ids
    List<PageSummary> summaries = this.query.query(this.factory)
        .where(this.tables.page().id.in(ids))
        .orderBy(ordering)
        .stream()
        .map(mapper::map)
        .collect(Accumulator.collecting(PageSummary::id, this.merger::merge))
        .stream().sorted(Ordering.explicit(ids).onResultOf(PageSummary::id))
        .toList();
    return new PageImpl<>(summaries, pageable, totalSize);
  }

}

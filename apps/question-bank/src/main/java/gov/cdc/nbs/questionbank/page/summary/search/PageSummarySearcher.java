package gov.cdc.nbs.questionbank.page.summary.search;

import com.querydsl.core.Tuple;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QPageCondMapping;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import gov.cdc.nbs.questionbank.exception.QueryException;
import gov.cdc.nbs.questionbank.page.PageSummaryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PageSummarySearcher {

  private static final QWaTemplate waTemplate = QWaTemplate.waTemplate;
  private static final QAuthUser authUser = QAuthUser.authUser;
  private static final QConditionCode conditionCode = QConditionCode.conditionCode;
  private static final QPageCondMapping conditionMapping = QPageCondMapping.pageCondMapping;
  private static final QCodeValueGeneral codeValueGeneral = QCodeValueGeneral.codeValueGeneral;


  private final PageSummaryMapper mapper;
  private final JPAQueryFactory factory;

  PageSummarySearcher(
      final PageSummaryMapper mapper,
      final JPAQueryFactory factory
  ) {
    this.mapper = mapper;
    this.factory = factory;
  }

  Page<PageSummary> find(
      final PageSummaryCriteria request,
      final Pageable pageable
  ) {
    // searches on page name and condition
    JPAQuery<Tuple> query = findApplicableSummaries(request);
    setOrderBy(query, pageable);

    // get distinct Ids and handle paging
    List<Long> distinctIds = query.stream()
        .map(tuple -> tuple.get(waTemplate.id))
        .distinct()
        .toList();

    List<Long> ids = distinctIds
        .subList((int) pageable.getOffset(),
            Math.min(
                distinctIds.size(),
                (int) pageable.getOffset() + pageable.getPageSize()
            )
        );

    int total = distinctIds.size();

    return fetchPageSummary(ids, pageable, total);
  }

  private JPAQuery<Tuple> findApplicableSummaries(final PageSummaryCriteria criteria) {
    return this.factory.select(
            waTemplate.id,
            waTemplate.templateType,
            waTemplate.templateNm,
            waTemplate.busObjType,
            waTemplate.lastChgTime,
            authUser.userFirstNm,
            authUser.userLastNm
        )
        .from(waTemplate)
        .leftJoin(authUser).on(waTemplate.lastChgUserId.eq(authUser.nedssEntryId))
        .leftJoin(conditionMapping).on(waTemplate.id.eq(conditionMapping.waTemplateUid.id))
        .leftJoin(conditionCode).on(conditionMapping.conditionCd.eq(conditionCode.id))
        .where(applyCriteria(criteria));
  }

  private BooleanExpression applyCriteria(final PageSummaryCriteria criteria) {
    // do not load legacy, template, or 'Published with Draft' pages
    // see legacy code PageManagementDAOImpl.java#2485
    BooleanExpression onlyPages = waTemplate.templateType.in("Draft", "Published");

    if (criteria.search() != null && !criteria.search().isBlank()) {
      String search = contains(criteria.search());
      // query template name and condition name
      return onlyPages.and(waTemplate.templateNm.like(search)
          .or(
              conditionCode.conditionShortNm.like(search)
                  .or(waTemplate.id.like(search)
                  )
          )
      );
    }
    return onlyPages;
  }

  private String contains(final String value) {
    return "%" + value + "%";
  }

  /**
   * Adds order by clauses to query. First order by is the user supplied clause, second is the identifier to prevent
   * non-unique sort exception. Supported fields are: id, name, eventType, status, lastUpdate, lastUpdatedBy
   */
  private void setOrderBy(final QueryBase<?> query, final Pageable pageable) {

    Sort.Order order = pageable.getSort().get().findFirst().orElse(null);
    if (order == null) {
      query.orderBy(waTemplate.id.desc());
      return;
    }

    OrderSpecifier<?> sort = resolveOrder(order);

    query.orderBy(sort);
    //    if (!order.getProperty().equals("id")) {
    //      query.orderBy(waTemplate.id.desc());
    //    }
  }

  private OrderSpecifier<?> resolveOrder(final Sort.Order order) {
    boolean isAscending = order.getDirection().isAscending();
    return switch (order.getProperty()) {
      case "id" -> isAscending ? waTemplate.id.asc() : waTemplate.id.desc();
      case "name" -> isAscending ? waTemplate.templateNm.asc().nullsFirst() : waTemplate.templateNm.desc();
      case "eventType" -> isAscending ? eventTypeOrder().asc() : eventTypeOrder().desc();
      case "status" -> isAscending ? waTemplate.templateType.asc() : waTemplate.templateType.desc();
      case "lastUpdate" -> isAscending ? waTemplate.lastChgTime.asc() : waTemplate.lastChgTime.desc();
      case "lastUpdateBy" -> isAscending ? authUser.userLastNm.asc().nullsFirst() : authUser.userLastNm.desc();
      default -> throw new QueryException(
          "Invalid sort specified. Allowed values are: [id, name, eventType , status, lastUpdate, lastUpdateBy]");
    };
  }

  // Provides custom ordering for bus_obj_Type column
  private NumberExpression<Integer> eventTypeOrder() {
    return waTemplate.busObjType
        .when("CON").then(1) // Contact
        .when("IXS").then(2) // Interview
        .when("INV").then(3) // Investigation
        .when("ISO").then(4) // Lab Isolate Tracking
        .when("LAB").then(5) // Lab Report
        .when("SUS").then(6) // Lab Susceptibility
        .when("VAC").then(7) // Vaccination
        .otherwise(8);
  }

  /**
   * Retrieves PageSummary objects for a list of Ids
   */
  private Page<PageSummary> fetchPageSummary(List<Long> ids, Pageable pageable, int totalSize) {
    // get the summaries based on supplied Ids
    JPAQuery<Tuple> query = factory.select(
            waTemplate.id,
            waTemplate.templateType,
            waTemplate.templateNm,
            waTemplate.descTxt,
            waTemplate.busObjType,
            waTemplate.lastChgTime,
            waTemplate.lastChgUserId,
            waTemplate.nndEntityIdentifier,
            waTemplate.publishVersionNbr,
            codeValueGeneral.codeShortDescTxt,
            authUser.userFirstNm,
            authUser.userLastNm,
            conditionCode.id,
            conditionCode.conditionShortNm
        ).from(waTemplate)
        .leftJoin(authUser).on(waTemplate.lastChgUserId.eq(authUser.nedssEntryId))
        .leftJoin(conditionMapping).on(waTemplate.id.eq(conditionMapping.waTemplateUid.id))
        .leftJoin(conditionCode).on(conditionMapping.conditionCd.eq(conditionCode.id))
        .leftJoin(codeValueGeneral).on(waTemplate.nndEntityIdentifier.eq(codeValueGeneral.id.code))
        .where(waTemplate.id.in(ids));
    setOrderBy(query, pageable);

    List<PageSummary> summaries = mapper.map(query.fetch());
    return new PageImpl<>(summaries, pageable, totalSize);
  }

}

package gov.cdc.nbs.questionbank.condition;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.request.ReadConditionRequest;
import gov.cdc.nbs.questionbank.entity.QPageCondMapping;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

@Component
public class ConditionSearcher {
  private static final QConditionCode conditionTable = QConditionCode.conditionCode;
  private static final QPageCondMapping pageMappingTable = QPageCondMapping.pageCondMapping;
  private static final QWaTemplate templateTable = QWaTemplate.waTemplate;

  private final JPAQueryFactory factory;

  public ConditionSearcher(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  // query for all conditions that are not associated with a page or are associated with the given
  // page
  public List<Condition> findAvailable(Long page) {
    return factory
        .select(
            conditionTable.id,
            conditionTable.conditionShortNm,
            conditionTable.progAreaCd,
            conditionTable.familyCd,
            conditionTable.coinfectionGrpCd,
            conditionTable.nndInd,
            conditionTable.investigationFormCd,
            conditionTable.statusCd)
        .from(conditionTable)
        .leftJoin(pageMappingTable)
        .on(pageMappingTable.conditionCd.eq(conditionTable.id))
        .where(resolveWhere(page))
        .orderBy(conditionTable.conditionShortNm.asc())
        .fetch()
        .stream()
        .map(this::toCondition)
        .toList();
  }

  private Predicate resolveWhere(Long page) {
    if (page != null) {
      return pageMappingTable.waTemplateUid.isNull().or(pageMappingTable.waTemplateUid.id.eq(page));
    } else {
      return pageMappingTable.waTemplateUid.isNull();
    }
  }

  public Page<Condition> search(ReadConditionRequest request, Pageable pageable) {
    // find all Ids of matching conditions
    List<String> ids =
        findConditions(request, pageable.getSort()).stream()
            .map(r -> r.get(conditionTable.id))
            .toList();
    int total = ids.size();

    // page the list of Ids
    ids =
        ids.subList(
            (int) pageable.getOffset(),
            Math.min(total, (int) pageable.getOffset() + pageable.getPageSize()));

    // fetch the conditions
    List<Condition> conditions =
        findConditionsById(ids, pageable.getSort()).stream().map(this::toCondition).toList();

    return new PageImpl<>(conditions, pageable, total);
  }

  private List<Tuple> findConditions(ReadConditionRequest request, Sort sort) {
    JPAQuery<Tuple> query =
        factory
            .select(
                conditionTable.id,
                conditionTable.conditionShortNm,
                conditionTable.progAreaCd,
                conditionTable.familyCd,
                conditionTable.coinfectionGrpCd,
                conditionTable.nndInd,
                conditionTable.investigationFormCd,
                conditionTable.statusCd)
            .distinct()
            .from(conditionTable)
            .leftJoin(pageMappingTable)
            .on(pageMappingTable.conditionCd.eq(conditionTable.id))
            .where(
                conditionTable
                    .id
                    .containsIgnoreCase(request.searchText())
                    .or(conditionTable.conditionShortNm.containsIgnoreCase(request.searchText())));

    if (request.excludeInUse()) {
      query.where(pageMappingTable.waTemplateUid.isNull());
    }
    return query.orderBy(resolveOrdering(sort)).fetch();
  }

  private OrderSpecifier<?> resolveOrdering(Sort sort) {
    if (sort == null || sort.isEmpty() || sort.isUnsorted()) {
      return conditionTable.conditionShortNm.asc();
    } else {
      Order order = sort.toList().get(0);
      var path =
          switch (order.getProperty()) {
            case "id" -> conditionTable.id;
            case "conditionShortNm" -> conditionTable.conditionShortNm;
            case "progAreaCd" -> conditionTable.progAreaCd;
            case "familyCd" -> conditionTable.familyCd;
            case "coinfection_grp_cd" -> conditionTable.coinfectionGrpCd;
            case "nndInd" -> conditionTable.nndInd;
            case "investigationFormCd" -> conditionTable.investigationFormCd;
            case "statusCd" -> conditionTable.statusCd;
            default -> conditionTable.conditionShortNm;
          };
      return order.isAscending() ? path.asc() : path.desc();
    }
  }

  private List<Tuple> findConditionsById(List<String> ids, Sort sort) {
    return factory
        .select(
            conditionTable.id,
            conditionTable.conditionShortNm,
            conditionTable.progAreaCd,
            conditionTable.familyCd,
            conditionTable.coinfectionGrpCd,
            conditionTable.nndInd,
            templateTable.templateNm,
            conditionTable.statusCd)
        .from(conditionTable)
        .leftJoin(pageMappingTable)
        .on(pageMappingTable.conditionCd.eq(conditionTable.id))
        .leftJoin(templateTable)
        .on(templateTable.id.eq(pageMappingTable.waTemplateUid.id))
        .where(conditionTable.id.in(ids))
        .orderBy(resolveOrdering(sort))
        .fetch();
  }

  private Condition toCondition(Tuple row) {
    return new Condition(
        row.get(conditionTable.id),
        row.get(conditionTable.conditionShortNm),
        row.get(conditionTable.progAreaCd),
        row.get(conditionTable.familyCd),
        row.get(conditionTable.coinfectionGrpCd),
        row.get(conditionTable.nndInd),
        row.get(conditionTable.investigationFormCd),
        row.get(conditionTable.statusCd));
  }
}

package gov.cdc.nbs.questionbank.valueset;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QCodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.QCodeset;
import gov.cdc.nbs.questionbank.valueset.model.ValueSetOption;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

@Component
public class ValueSetOptionFinder {
  private static final QCodeset valuesetTable = QCodeset.codeset;
  private static final QCodeSetGroupMetadatum metadataTable =
      QCodeSetGroupMetadatum.codeSetGroupMetadatum;
  final JPAQueryFactory factory;

  public ValueSetOptionFinder(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  public List<ValueSetOption> findAllValueSetOptions() {
    return factory
        .select(
            metadataTable.id,
            metadataTable.codeSetShortDescTxt,
            metadataTable.codeSetNm,
            metadataTable.codeSetDescTxt,
            valuesetTable.valueSetTypeCd)
        .from(metadataTable)
        .leftJoin(valuesetTable)
        .on(valuesetTable.codeSetGroup.id.eq(metadataTable.id))
        .where(
            metadataTable
                .ldfPicklistIndCd
                .eq('Y')
                .and(valuesetTable.statusCd.eq("A"))
                .and(
                    metadataTable.id.in(
                        JPAExpressions.select(valuesetTable.codeSetGroup.id)
                            .from(valuesetTable)
                            .where(
                                valuesetTable
                                    .id
                                    .classCd
                                    .eq("code_value_general")
                                    .and(valuesetTable.statusCd.eq("A"))))))
        .orderBy(metadataTable.codeSetShortDescTxt.asc())
        .fetch()
        .stream()
        .map(this::toOption)
        .toList();
  }

  public Page<ValueSetOption> search(ValueSetSearchRequest request, Pageable pageable) {
    OrderSpecifier<?>[] orders = resolveOrdering(pageable);

    // Get the value set Ids that match the provided criteria
    List<Long> allIds =
        findIds(request.query()).orderBy(orders).fetch().stream()
            .map(tuple -> tuple.get(metadataTable.id))
            .toList();

    int total = allIds.size();

    // Apply paging to list of Ids
    List<Long> ids =
        allIds.subList(
            (int) pageable.getOffset(),
            Math.min(total, (int) pageable.getOffset() + pageable.getPageSize()));

    // Fetch the value set data for the Ids
    List<ValueSetOption> valuesets = findByIds(ids, orders);

    return new PageImpl<>(valuesets, pageable, total);
  }

  private JPAQuery<Tuple> findIds(String query) {
    query = "%" + query + "%";
    return factory
        .select(
            metadataTable.id,
            metadataTable.codeSetShortDescTxt,
            metadataTable.codeSetNm,
            metadataTable.codeSetDescTxt,
            valuesetTable.valueSetTypeCd)
        .from(metadataTable)
        .join(valuesetTable)
        .on(valuesetTable.codeSetGroup.id.eq(metadataTable.id))
        .where(
            metadataTable
                .ldfPicklistIndCd
                .eq('Y')
                .and(valuesetTable.statusCd.eq("A"))
                .and(
                    metadataTable
                        .id
                        .in(
                            JPAExpressions.select(valuesetTable.codeSetGroup.id)
                                .from(valuesetTable)
                                .where(
                                    valuesetTable
                                        .id
                                        .classCd
                                        .eq("code_value_general")
                                        .and(valuesetTable.statusCd.eq("A"))))
                        .and(
                            metadataTable
                                .codeSetShortDescTxt
                                .like(query)
                                .or(
                                    metadataTable
                                        .codeSetDescTxt
                                        .like(query)
                                        .or(metadataTable.codeSetNm.like(query))
                                        .or(metadataTable.id.like(query))))));
  }

  private List<ValueSetOption> findByIds(List<Long> ids, OrderSpecifier<?>[] orders) {
    return factory
        .select(
            metadataTable.id,
            metadataTable.codeSetShortDescTxt,
            metadataTable.codeSetNm,
            metadataTable.codeSetDescTxt,
            valuesetTable.valueSetTypeCd)
        .from(metadataTable)
        .join(valuesetTable)
        .on(valuesetTable.codeSetGroup.id.eq(metadataTable.id))
        .where(metadataTable.id.in(ids))
        .orderBy(orders)
        .fetch()
        .stream()
        .map(this::toOption)
        .toList();
  }

  private OrderSpecifier<?> resolveOrder(Order order) {
    return switch (order.getProperty().toLowerCase()) {
      case "type" -> applyOrder(order, valuesetTable.valueSetTypeCd);
      case "name" -> applyOrder(order, metadataTable.codeSetShortDescTxt);
      case "description" -> applyOrder(order, metadataTable.codeSetDescTxt);
      case "code" -> applyOrder(order, metadataTable.id);
      default -> metadataTable.codeSetShortDescTxt.asc();
    };
  }

  private OrderSpecifier<String> applyOrder(Order order, StringPath path) {
    return order.isAscending() ? path.asc() : path.desc();
  }

  private OrderSpecifier<Long> applyOrder(Order order, NumberPath<Long> path) {
    return order.isAscending() ? path.asc() : path.desc();
  }

  private OrderSpecifier<?>[] resolveOrdering(final Pageable pageable) {
    return Stream.concat(
            pageable.getSort().map(this::resolveOrder).stream(),
            Stream.of(metadataTable.codeSetNm.asc()))
        .filter(Objects::nonNull)
        .toArray(OrderSpecifier[]::new);
  }

  private ValueSetOption toOption(Tuple row) {
    return new ValueSetOption(
        row.get(metadataTable.id),
        row.get(metadataTable.id).toString(),
        row.get(metadataTable.codeSetShortDescTxt),
        row.get(metadataTable.codeSetNm),
        row.get(metadataTable.codeSetDescTxt),
        row.get(valuesetTable.valueSetTypeCd).toUpperCase());
  }
}

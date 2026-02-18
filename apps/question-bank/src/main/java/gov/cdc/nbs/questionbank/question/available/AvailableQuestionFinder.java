package gov.cdc.nbs.questionbank.question.available;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

/** Used for finding questions that can be added to the specified page. */
@Component
public class AvailableQuestionFinder {
  private final JPAQueryFactory factory;
  private final AvailableQuestionTables tables;

  public AvailableQuestionFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new AvailableQuestionTables();
  }

  public Page<AvailableQuestion> find(
      final AvailableQuestionCriteria criteria, final Long page, final Pageable pageable) {
    OrderSpecifier<?>[] orders = resolveOrdering(pageable);

    // Get all the Ids for questions in the current page
    List<Long> inUseIds = findInUseQuestions(page);

    // Get the question Ids that match the provided criteria
    List<Long> allIds =
        findPossibleQuestionIds(criteria, inUseIds).orderBy(orders).stream()
            .map(tuple -> tuple.get(this.tables.question().id))
            .toList();

    int total = allIds.size();

    // Apply paging to list of Ids
    List<Long> ids =
        allIds.subList(
            (int) pageable.getOffset(),
            Math.min(total, (int) pageable.getOffset() + pageable.getPageSize()));

    // Fetch the question data for the Ids
    List<AvailableQuestion> questions = findByIds(ids, orders);

    return new PageImpl<>(questions, pageable, total);
  }

  private List<Long> findInUseQuestions(final Long page) {
    return this.factory
        .select(this.tables.question().id)
        .from(this.tables.question())
        .join(this.tables.uiMetadata())
        .on(
            this.tables
                .question()
                .questionIdentifier
                .eq(this.tables.uiMetadata().questionIdentifier))
        .where(this.tables.uiMetadata().waTemplateUid.id.eq(page))
        .stream()
        .toList();
  }

  private JPAQuery<Tuple> findPossibleQuestionIds(
      final AvailableQuestionCriteria criteria, final List<Long> inUse) {
    return this.factory
        .select(
            this.tables.question().id,
            this.tables.question().questionType,
            this.tables.question().questionIdentifier,
            this.tables.question().questionLabel,
            this.tables.question().recordStatusCd,
            this.tables.codeValueGeneral().codeShortDescTxt)
        .distinct()
        .from(this.tables.question())
        .leftJoin(this.tables.codeset())
        .on(this.tables.codeset().codeSetGroup.id.eq(this.tables.question().codeSetGroupId))
        .leftJoin(this.tables.codeValueGeneral())
        .on(
            this.tables
                .codeValueGeneral()
                .id
                .code
                .eq(this.tables.question().subGroupNm)
                .and(this.tables.codeValueGeneral().id.codeSetNm.eq("NBS_QUES_SUBGROUP")))
        .leftJoin(this.tables.uiComponent())
        .on(this.tables.uiComponent().id.eq(this.tables.question().nbsUiComponentUid))
        .where(applySearch(criteria, inUse));
  }

  private List<AvailableQuestion> findByIds(List<Long> ids, OrderSpecifier<?>[] orders) {
    return this.factory
        .select(
            // Basic question info
            this.tables.question().id,
            this.tables.question().questionType,
            this.tables.question().recordStatusCd,
            this.tables.question().dataType,
            this.tables.question().otherValueIndCd,
            this.tables.question().questionIdentifier,
            this.tables.question().subGroupNm,
            this.tables.codeValueGeneral().codeShortDescTxt,
            this.tables.question().codeSetGroupId,
            this.tables.codeset().valueSetNm,
            this.tables.question().questionNm,
            this.tables.question().descTxt,
            // UI info
            this.tables.question().questionLabel,
            this.tables.question().questionToolTip,
            this.tables.question().nbsUiComponentUid,
            this.tables.uiComponent().typeCdDesc,
            // Datamart - Reporting
            this.tables.question().rptAdminColumnNm,
            this.tables.question().rdbTableNm,
            this.tables.question().rdbColumnNm,
            this.tables.question().userDefinedColumnNm,
            // Messaging
            this.tables.question().nndMsgInd,
            this.tables.question().questionIdentifierNnd,
            this.tables.question().questionOidSystemTxt,
            this.tables.question().questionRequiredNnd,
            this.tables.question().questionDataTypeNnd,
            this.tables.question().hl7SegmentField,
            // Admin
            this.tables.question().adminComment)
        .from(this.tables.question())
        .leftJoin(this.tables.codeset())
        .on(this.tables.codeset().codeSetGroup.id.eq(this.tables.question().codeSetGroupId))
        .leftJoin(this.tables.codeValueGeneral())
        .on(
            this.tables
                .codeValueGeneral()
                .id
                .code
                .eq(this.tables.question().subGroupNm)
                .and(this.tables.codeValueGeneral().id.codeSetNm.eq("NBS_QUES_SUBGROUP")))
        .leftJoin(this.tables.uiComponent())
        .on(this.tables.uiComponent().id.eq(this.tables.question().nbsUiComponentUid))
        .where(this.tables.question().id.in(ids))
        .orderBy(orders)
        .stream()
        .map(AvailableQuestionMapper::toQuestion)
        .toList();
  }

  private BooleanExpression applySearch(
      final AvailableQuestionCriteria criteria, final List<Long> inUse) {
    return Stream.concat(applyCriteria(criteria), questionNotInUseAndActive(inUse))
        .reduce(BooleanExpression::and)
        .orElseThrow();
  }

  private Stream<BooleanExpression> applyCriteria(final AvailableQuestionCriteria criteria) {
    if (criteria.query() != null && !criteria.query().isBlank()) {
      String query = "%" + criteria.query() + "%";
      return Stream.of(
          this.tables
              .question()
              .questionType
              .like(query)
              .or(this.tables.question().questionLabel.like(query))
              .or(this.tables.question().questionIdentifier.like(query))
              .or(this.tables.question().questionNm.like(query))
              .or(this.tables.codeValueGeneral().codeShortDescTxt.like(query))
              .or(this.tables.question().id.like(query)));
    }
    return Stream.empty();
  }

  /**
   * Limit questions to those not currently in use on the provided page and are Active
   *
   * @param page
   * @return
   */
  private Stream<BooleanExpression> questionNotInUseAndActive(final List<Long> inUse) {
    return Stream.of(
        this.tables
            .question()
            .id
            .notIn(inUse)
            .and(this.tables.question().recordStatusCd.eq("Active")));
  }

  private OrderSpecifier<?> resolveOrder(Order order) {
    return switch (order.getProperty().toLowerCase()) {
      case "status" -> applyOrder(order, this.tables.question().recordStatusCd);
      case "type" -> applyOrder(order, this.tables.question().questionType);
      case "uniqueid" -> applyOrder(order, this.tables.question().questionIdentifier);
      case "label" -> applyOrder(order, this.tables.question().questionLabel);
      case "subgroup" -> applyOrder(order, this.tables.codeValueGeneral().codeShortDescTxt);
      default -> null;
    };
  }

  private OrderSpecifier<String> applyOrder(Order order, StringPath path) {
    return order.isAscending() ? path.asc() : path.desc();
  }

  private OrderSpecifier<?>[] resolveOrdering(final Pageable pageable) {
    return Stream.concat(
            pageable.getSort().map(this::resolveOrder).stream(),
            Stream.of(this.tables.question().id.desc()))
        .filter(Objects::nonNull)
        .toArray(OrderSpecifier[]::new);
  }
}

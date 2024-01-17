package gov.cdc.nbs.questionbank.filter.querydsl;

import java.util.Collection;
import java.util.stream.Stream;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import gov.cdc.nbs.questionbank.entity.QPageCondMapping;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import gov.cdc.nbs.questionbank.filter.Filter;
import gov.cdc.nbs.questionbank.filter.MultiValueFilter;
import gov.cdc.nbs.questionbank.filter.ValueFilter.Operator;

public class PageLibraryQueryDSLCriteriaResolver extends DefaultQueryDSLCriteriaResolver {

  private static final QWaTemplate pageTable = QWaTemplate.waTemplate;
  private static final QPageCondMapping mappingTable = QPageCondMapping.pageCondMapping;
  private static final QConditionCode conditionTable = QConditionCode.conditionCode;


  @Override
  public Stream<BooleanExpression> resolve(Filter filter, Expression<?> expression) {
    // If query is conditions not in, add custom handler
    if (filter instanceof MultiValueFilter multi &&
        expression instanceof StringExpression &&
        multi.property().equals("conditions") &&
        Operator.NOT_EQUAL_TO.equals(multi.operator())) {
      return Stream.of(conditionNotIn(multi.values()));
    }

    // else provide default resolution
    return super.resolve(filter, expression);
  }

  BooleanExpression conditionNotIn(Collection<String> conditions) {
    return pageTable.id.notIn(JPAExpressions.select(pageTable.id)
        .from(pageTable)
        .leftJoin(mappingTable)
        .on(pageTable.id.eq(mappingTable.waTemplateUid.id))
        .leftJoin(conditionTable)
        .on(mappingTable.conditionCd.eq(conditionTable.id))
        .where(conditionTable.conditionShortNm.in(conditions)));
  }
}

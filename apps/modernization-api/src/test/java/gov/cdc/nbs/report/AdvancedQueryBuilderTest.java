package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.models.AdvancedQuery;
import java.util.List;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

public class AdvancedQueryBuilderTest {
  private final Faker faker = new Faker();

  private final Report report = mock(Report.class);
  private final FilterCode filterCode =
      FilterCode.builder()
          .codeTable("NONE")
          .filterType("ADV_WCB")
          .filterName("Where Clause Builder")
          .build();

  private final ReportFilter filter =
      ReportFilter.builder().report(report).filterCode(filterCode).build();

  private FilterValue buildOperatorValue(Integer sequenceNumber, String operator) {
    return FilterValue.builder()
        .id(faker.number().randomNumber())
        .reportFilter(filter)
        .sequenceNumber(sequenceNumber)
        .valueType("OPERATOR")
        .operator(operator)
        .build();
  }

  private FilterValue buildClauseValue(Integer sequenceNumber, String operator, String valueTxt) {
    return FilterValue.builder()
        .id(faker.number().randomNumber())
        .reportFilter(filter)
        .sequenceNumber(sequenceNumber)
        .valueType("CLAUSE")
        .columnUid(faker.number().randomNumber())
        .operator(operator)
        .valueTxt(valueTxt)
        .build();
  }

  ////////////////////////////////////////////////////////////////////////

  private void assertRuleMatchesClauseValue(AdvancedQuery.Rule rule, FilterValue clauseValue) {
    assertThat(rule.id()).isEqualTo(clauseValue.getId().toString());
    assertThat(rule.columnId()).isEqualTo(clauseValue.getColumnUid());
    assertThat(rule.operator()).isEqualTo(clauseValue.getOperator());
    assertThat(rule.value()).isEqualTo(clauseValue.getValueTxt());
  }

  private AdvancedQuery.RuleGroup tryBuild(AdvancedQueryBuilder builder) {
    try {
      return builder.build();
    } catch (AdvancedQueryException e) {
      System.out.println(e);
      assertFalse(true);
      return null;
    }
  }

  ////////////////////////////////////////////////////////////////////////

  @Test
  void build_should_create_rule_group_with_single_rule() {
    FilterValue equalsClause = buildClauseValue(1, "equals", "test value");
    filter.setFilterValues(List.of(equalsClause));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());

    AdvancedQuery.RuleGroup result = tryBuild(builder);

    // Root 'AND' RuleGroup should be created
    assertThat(result.combinator()).isEqualTo(ReportConstants.QueryCombinators.and);
    assertThat(result.rules()).hasSize(1);

    // Single Rule on said RuleGroup should match equals clause
    assertThat(result.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule = (AdvancedQuery.Rule) result.rules().getFirst();

    assertRuleMatchesClauseValue(rule, equalsClause);
  }

  @Test
  void build_should_create_rule_group_with_multiple_rules_in_single_or_group() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();
    FilterValue containsClause = buildClauseValue(1, "contains", "value1");
    FilterValue orOperator = buildOperatorValue(2, "or");
    FilterValue startsWithClause = buildClauseValue(3, "startsWith", "value2");

    filter.setFilterValues(List.of(containsClause, orOperator, startsWithClause));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());

    AdvancedQuery.RuleGroup result = tryBuild(builder);

    // Root 'OR' RuleGroup should be created, with 2 rules
    assertThat(result.combinator()).isEqualTo(ReportConstants.QueryCombinators.or);
    assertThat(result.rules()).hasSize(2);

    // The first rule in the 'OR' group should match the 'contains' clause
    assertThat(result.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule1 = (AdvancedQuery.Rule) result.rules().getFirst();
    assertRuleMatchesClauseValue(rule1, containsClause);

    // The second rule in the 'OR' group should match the 'startsWith' clause
    assertThat(result.rules().get(1)).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule2 = (AdvancedQuery.Rule) result.rules().get(1);
    assertRuleMatchesClauseValue(rule2, startsWithClause);
  }

  @Test
  void build_should_create_rule_group_with_both_OR_AND_operators() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue containsClause = buildClauseValue(1, "contains", "value3");
    FilterValue andValue = buildOperatorValue(2, "and");
    FilterValue equalsClause = buildClauseValue(3, "equals", "value1");
    FilterValue orValue = buildOperatorValue(4, "or");
    FilterValue notEqualsClause = buildClauseValue(5, "notEquals", "value2");

    filter.setFilterValues(
        List.of(containsClause, andValue, equalsClause, orValue, notEqualsClause));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());

    AdvancedQuery.RuleGroup rootRuleGroup = tryBuild(builder);

    // Root 'OR' RuleGroup should be created, with 2 rules
    assertThat(rootRuleGroup.combinator()).isEqualTo(ReportConstants.QueryCombinators.or);
    assertThat(rootRuleGroup.rules()).hasSize(2);

    // The first rule in the root 'OR' RuleGroup is the 'AND' RuleGroup
    assertThat(rootRuleGroup.rules().getFirst()).isInstanceOf(AdvancedQuery.RuleGroup.class);
    AdvancedQuery.RuleGroup andGroup = (AdvancedQuery.RuleGroup) rootRuleGroup.rules().getFirst();

    assertThat(andGroup.combinator()).isEqualTo(ReportConstants.QueryCombinators.and);
    assertThat(andGroup.rules()).hasSize(2);

    // The first Rule in the 'AND' group should match the 'contains' clause
    assertThat(andGroup.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule1 = (AdvancedQuery.Rule) andGroup.rules().getFirst();
    assertRuleMatchesClauseValue(rule1, containsClause);

    // The second Rule in the 'AND' group should match the 'equals' clause
    assertThat(andGroup.rules().get(1)).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule2 = (AdvancedQuery.Rule) andGroup.rules().get(1);
    assertRuleMatchesClauseValue(rule2, equalsClause);

    // The second rule in the root 'OR' RuleGroup is the 'notEquals' clause
    assertThat(rootRuleGroup.rules().get(1)).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule notEqualsRule = (AdvancedQuery.Rule) rootRuleGroup.rules().get(1);
    assertRuleMatchesClauseValue(notEqualsRule, notEqualsClause);
  }

  @Test
  void build_should_handle_outermost_parentheses_correctly() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue openParenValue = buildOperatorValue(1, "(");
    FilterValue containsClause = buildClauseValue(2, "contains", "value3");
    FilterValue andValue = buildOperatorValue(3, "and");
    FilterValue equalsClause = buildClauseValue(4, "equals", "value1");
    FilterValue orValue = buildOperatorValue(5, "or");
    FilterValue notEqualsClause = buildClauseValue(6, "notEquals", "value2");
    FilterValue closeParenValue = buildOperatorValue(7, ")");

    filter.setFilterValues(
        List.of(
            openParenValue,
            containsClause,
            andValue,
            equalsClause,
            orValue,
            notEqualsClause,
            closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());

    AdvancedQuery.RuleGroup rootRuleGroup = tryBuild(builder);

    // Root 'OR' RuleGroup should be created, with 2 rules
    assertThat(rootRuleGroup.combinator()).isEqualTo(ReportConstants.QueryCombinators.or);
    assertThat(rootRuleGroup.rules()).hasSize(2);

    // The first rule in the root 'OR' RuleGroup is the 'AND' RuleGroup
    assertThat(rootRuleGroup.rules().getFirst()).isInstanceOf(AdvancedQuery.RuleGroup.class);
    AdvancedQuery.RuleGroup andGroup = (AdvancedQuery.RuleGroup) rootRuleGroup.rules().getFirst();

    assertThat(andGroup.combinator()).isEqualTo(ReportConstants.QueryCombinators.and);
    assertThat(andGroup.rules()).hasSize(2);

    // The first Rule in the 'AND' group should match the 'contains' clause
    assertThat(andGroup.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule1 = (AdvancedQuery.Rule) andGroup.rules().getFirst();
    assertRuleMatchesClauseValue(rule1, containsClause);

    // The second Rule in the 'AND' group should match the 'equals' clause
    assertThat(andGroup.rules().get(1)).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule2 = (AdvancedQuery.Rule) andGroup.rules().get(1);
    assertRuleMatchesClauseValue(rule2, equalsClause);

    // The second rule in the root 'OR' RuleGroup is the 'notEquals' clause
    assertThat(rootRuleGroup.rules().get(1)).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule notEqualsRule = (AdvancedQuery.Rule) rootRuleGroup.rules().get(1);
    assertRuleMatchesClauseValue(notEqualsRule, notEqualsClause);
  }

  @Test
  void build_should_handle_OR_nested_within_AND_correctly() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue containsClause = buildClauseValue(1, "contains", "value3");
    FilterValue andValue = buildOperatorValue(2, "and");
    FilterValue openParenValue = buildOperatorValue(3, "(");
    FilterValue equalsClause = buildClauseValue(4, "equals", "value1");
    FilterValue orValue = buildOperatorValue(5, "or");
    FilterValue notEqualsClause = buildClauseValue(6, "notEquals", "value2");
    FilterValue closeParenValue = buildOperatorValue(7, ")");

    filter.setFilterValues(
        List.of(
            containsClause,
            andValue,
            openParenValue,
            equalsClause,
            orValue,
            notEqualsClause,
            closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());

    AdvancedQuery.RuleGroup rootRuleGroup = tryBuild(builder);

    // Root 'AND' RuleGroup should be created, with 2 rules
    assertThat(rootRuleGroup.combinator()).isEqualTo(ReportConstants.QueryCombinators.and);
    assertThat(rootRuleGroup.rules()).hasSize(2);

    // The first Rule in the root 'AND' group should match the 'contains' clause
    assertThat(rootRuleGroup.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule1 = (AdvancedQuery.Rule) rootRuleGroup.rules().getFirst();
    assertRuleMatchesClauseValue(rule1, containsClause);

    // The second rule in the root 'AND' RuleGroup is the 'OR' RuleGroup, with 2 rules
    assertThat(rootRuleGroup.rules().get(1)).isInstanceOf(AdvancedQuery.RuleGroup.class);
    AdvancedQuery.RuleGroup andGroup = (AdvancedQuery.RuleGroup) rootRuleGroup.rules().get(1);

    assertThat(andGroup.combinator()).isEqualTo(ReportConstants.QueryCombinators.or);
    assertThat(andGroup.rules()).hasSize(2);

    // The first Rule in the 'AND' group should match the 'equals' clause
    assertThat(andGroup.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule equalsRule = (AdvancedQuery.Rule) andGroup.rules().getFirst();
    assertRuleMatchesClauseValue(equalsRule, equalsClause);

    // The second Rule in the 'AND' group should match the 'notEquals' clause
    assertThat(andGroup.rules().get(1)).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule notEqualsRule = (AdvancedQuery.Rule) andGroup.rules().get(1);
    assertRuleMatchesClauseValue(notEqualsRule, notEqualsClause);
  }

  @Test
  void build_should_throw_on_invalid_expression_double_close_paren() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue openParenValue1 = buildOperatorValue(1, "(");
    FilterValue openParenValue2 = buildOperatorValue(2, "(");
    FilterValue closeParenValue = buildOperatorValue(3, ")");

    filter.setFilterValues(List.of(openParenValue1, openParenValue2, closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_paren_with_three_clauses() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue containsClause = buildOperatorValue(1, "(");
    FilterValue equalsClause = buildClauseValue(2, "equals", "value1");
    FilterValue notEqualsClause = buildClauseValue(3, "notEquals", "value2");
    FilterValue notNullClause = buildClauseValue(4, "notNull", "value3");
    FilterValue closeParenValue = buildOperatorValue(5, ")");

    filter.setFilterValues(
        List.of(containsClause, equalsClause, notEqualsClause, notNullClause, closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_or_and() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue orOperatorValue = buildOperatorValue(1, "or");
    FilterValue andOperatorValue = buildOperatorValue(2, "and");

    filter.setFilterValues(List.of(orOperatorValue, andOperatorValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_a_or_and() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue equalsClause = buildClauseValue(1, "equals", "value1");
    FilterValue orOperatorValue = buildOperatorValue(2, "or");
    FilterValue andOperatorValue = buildOperatorValue(3, "and");

    filter.setFilterValues(List.of(equalsClause, orOperatorValue, andOperatorValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_a_or_a_a() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue equalsClause = buildClauseValue(1, "equals", "value1");
    FilterValue orOperatorValue = buildOperatorValue(2, "or");
    FilterValue notEqualsClause = buildClauseValue(3, "notEquals", "value2");
    FilterValue notNullClause = buildClauseValue(4, "notNull", "value3");

    filter.setFilterValues(List.of(equalsClause, orOperatorValue, notEqualsClause, notNullClause));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_or_b() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue orOperatorValue = buildOperatorValue(1, "or");
    FilterValue notEqualsClause = buildClauseValue(2, "notEquals", "value2");

    filter.setFilterValues(List.of(orOperatorValue, notEqualsClause));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_empty_paren_or_a() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue openParenValue = buildOperatorValue(1, "(");
    FilterValue closeParenValue = buildOperatorValue(2, ")");
    FilterValue orOperatorValue = buildOperatorValue(3, "or");
    FilterValue notEqualsClause = buildClauseValue(4, "notEquals", "value2");

    filter.setFilterValues(
        List.of(openParenValue, closeParenValue, orOperatorValue, notEqualsClause));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_empty_paren() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue openParenValue = buildOperatorValue(1, "(");
    FilterValue closeParenValue = buildOperatorValue(2, ")");

    filter.setFilterValues(List.of(openParenValue, closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_single_close() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue closeParenValue = buildOperatorValue(1, ")");

    filter.setFilterValues(List.of(closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_single_or() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue orOperatorValue = buildOperatorValue(1, "or");

    filter.setFilterValues(List.of(orOperatorValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_single_and() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue andOperatorValue = buildOperatorValue(1, "and");

    filter.setFilterValues(List.of(andOperatorValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_single_open() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue openParenValue = buildOperatorValue(1, "(");

    filter.setFilterValues(List.of(openParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }

  @Test
  void build_should_throw_on_invalid_expression_open_open_close() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue openParenValue1 = buildOperatorValue(1, "(");
    FilterValue openParenValue2 = buildOperatorValue(1, "(");
    FilterValue closeParenValue = buildOperatorValue(1, "(");

    filter.setFilterValues(List.of(openParenValue1, openParenValue2, closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, () -> tryBuild(builder));
  }
}

// valid
// ((((a or b))))
// ((a and b or c and (d or e)))
// (((b)) or a)
// a and b or c and d and e or f

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

  /// /////////////////////////////////////////////////////////////////////

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
      assertFalse(true, "AdvancedQueryBuilder should not throw an exception");
      return null;
    }
  }

  /// /////////////////////////////////////////////////////////////////////

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
    FilterValue openParenValue1 = buildOperatorValue(1, "(");
    FilterValue openParenValue2 = buildOperatorValue(2, "(");
    FilterValue closeParenValue = buildOperatorValue(3, ")");

    filter.setFilterValues(List.of(openParenValue1, openParenValue2, closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_paren_with_three_clauses() {
    FilterValue containsClause = buildOperatorValue(1, "(");
    FilterValue equalsClause = buildClauseValue(2, "equals", "value1");
    FilterValue notEqualsClause = buildClauseValue(3, "notEquals", "value2");
    FilterValue notNullClause = buildClauseValue(4, "notNull", "value3");
    FilterValue closeParenValue = buildOperatorValue(5, ")");

    filter.setFilterValues(
        List.of(containsClause, equalsClause, notEqualsClause, notNullClause, closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_or_and() {
    FilterValue orOperatorValue = buildOperatorValue(1, "or");
    FilterValue andOperatorValue = buildOperatorValue(2, "and");

    filter.setFilterValues(List.of(orOperatorValue, andOperatorValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_a_or_and() {
    FilterValue equalsClause = buildClauseValue(1, "equals", "value1");
    FilterValue orOperatorValue = buildOperatorValue(2, "or");
    FilterValue andOperatorValue = buildOperatorValue(3, "and");

    filter.setFilterValues(List.of(equalsClause, orOperatorValue, andOperatorValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_a_or_a_a() {
    FilterValue equalsClause = buildClauseValue(1, "equals", "value1");
    FilterValue orOperatorValue = buildOperatorValue(2, "or");
    FilterValue notEqualsClause = buildClauseValue(3, "notEquals", "value2");
    FilterValue notNullClause = buildClauseValue(4, "notNull", "value3");

    filter.setFilterValues(List.of(equalsClause, orOperatorValue, notEqualsClause, notNullClause));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_or_b() {
    FilterValue orOperatorValue = buildOperatorValue(1, "or");
    FilterValue notEqualsClause = buildClauseValue(2, "notEquals", "value2");

    filter.setFilterValues(List.of(orOperatorValue, notEqualsClause));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_empty_paren_or_a() {
    FilterValue openParenValue = buildOperatorValue(1, "(");
    FilterValue closeParenValue = buildOperatorValue(2, ")");
    FilterValue orOperatorValue = buildOperatorValue(3, "or");
    FilterValue notEqualsClause = buildClauseValue(4, "notEquals", "value2");

    filter.setFilterValues(
        List.of(openParenValue, closeParenValue, orOperatorValue, notEqualsClause));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_empty_paren() {
    FilterValue openParenValue = buildOperatorValue(1, "(");
    FilterValue closeParenValue = buildOperatorValue(2, ")");

    filter.setFilterValues(List.of(openParenValue, closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_single_close() {
    FilterValue closeParenValue = buildOperatorValue(1, ")");

    filter.setFilterValues(List.of(closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_single_or() {
    FilterValue orOperatorValue = buildOperatorValue(1, "or");

    filter.setFilterValues(List.of(orOperatorValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_single_and() {
    FilterValue andOperatorValue = buildOperatorValue(1, "and");

    filter.setFilterValues(List.of(andOperatorValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_single_open() {
    FilterValue openParenValue = buildOperatorValue(1, "(");

    filter.setFilterValues(List.of(openParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_throw_on_invalid_expression_open_open_close() {
    FilterValue openParenValue1 = buildOperatorValue(1, "(");
    FilterValue openParenValue2 = buildOperatorValue(1, "(");
    FilterValue closeParenValue = buildOperatorValue(1, "(");

    filter.setFilterValues(List.of(openParenValue1, openParenValue2, closeParenValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    assertThrows(AdvancedQueryException.class, builder::build);
  }

  @Test
  void build_should_handle_deeply_nested_or_expression() {
    // ((((a or b))))
    FilterValue openParen1 = buildOperatorValue(1, "(");
    FilterValue openParen2 = buildOperatorValue(2, "(");
    FilterValue openParen3 = buildOperatorValue(3, "(");
    FilterValue openParen4 = buildOperatorValue(4, "(");

    FilterValue equalsClause = buildClauseValue(5, "equals", "value1");
    FilterValue orOperator = buildOperatorValue(6, "or");
    FilterValue notEqualsClause = buildClauseValue(7, "notEquals", "value2");

    FilterValue closeParen1 = buildOperatorValue(8, ")");
    FilterValue closeParen2 = buildOperatorValue(9, ")");
    FilterValue closeParen3 = buildOperatorValue(10, ")");
    FilterValue closeParen4 = buildOperatorValue(11, ")");

    filter.setFilterValues(
        List.of(
            openParen1,
            openParen2,
            openParen3,
            openParen4,
            equalsClause,
            orOperator,
            notEqualsClause,
            closeParen1,
            closeParen2,
            closeParen3,
            closeParen4));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    AdvancedQuery.RuleGroup root = tryBuild(builder);

    // should simplify to an OR group with two rules (a, b)
    assertThat(root.combinator()).isEqualTo(ReportConstants.QueryCombinators.or);
    assertThat(root.rules()).hasSize(2);
    assertThat(root.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) root.rules().getFirst(), equalsClause);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) root.rules().get(1), notEqualsClause);
  }

  @Test
  void build_should_handle_complex_mixed_and_or_with_inner_parens() {
    // ((a and b or c and (d or e)))
    FilterValue openParen1 = buildOperatorValue(1, "(");
    FilterValue openParen2 = buildOperatorValue(2, "(");

    FilterValue equalsClause1 = buildClauseValue(3, "equals", "value1");
    FilterValue andOperator1 = buildOperatorValue(4, "and");
    FilterValue equalsClause2 = buildClauseValue(5, "equals", "value2");
    FilterValue orOperator1 = buildOperatorValue(6, "or");
    FilterValue equalsClause3 = buildClauseValue(7, "equals", "value3");
    FilterValue andOperator2 = buildOperatorValue(8, "and");
    FilterValue innerOpenParen = buildOperatorValue(9, "(");
    FilterValue equalsClause4 = buildClauseValue(10, "equals", "value4");
    FilterValue orOperator2 = buildOperatorValue(11, "or");
    FilterValue equalsClause5 = buildClauseValue(12, "equals", "value5");
    FilterValue innerCloseParen = buildOperatorValue(13, ")");
    FilterValue closeParen1 = buildOperatorValue(14, ")");
    FilterValue closeParen2 = buildOperatorValue(15, ")");

    filter.setFilterValues(
        List.of(
            openParen1,
            openParen2,
            equalsClause1,
            andOperator1,
            equalsClause2,
            orOperator1,
            equalsClause3,
            andOperator2,
            innerOpenParen,
            equalsClause4,
            orOperator2,
            equalsClause5,
            innerCloseParen,
            closeParen1,
            closeParen2));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    AdvancedQuery.RuleGroup root = tryBuild(builder);

    // Root should be OR combining two AND groups
    assertThat(root.combinator()).isEqualTo(ReportConstants.QueryCombinators.or);
    assertThat(root.rules()).hasSize(2);

    // First rule is AND group (a, b)
    assertThat(root.rules().getFirst()).isInstanceOf(AdvancedQuery.RuleGroup.class);
    AdvancedQuery.RuleGroup firstAnd = (AdvancedQuery.RuleGroup) root.rules().getFirst();
    assertThat(firstAnd.combinator()).isEqualTo(ReportConstants.QueryCombinators.and);
    assertThat(firstAnd.rules()).hasSize(2);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) firstAnd.rules().getFirst(), equalsClause1);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) firstAnd.rules().get(1), equalsClause2);

    // Second rule is AND group (c, (d or e))
    assertThat(root.rules().get(1)).isInstanceOf(AdvancedQuery.RuleGroup.class);
    AdvancedQuery.RuleGroup secondAnd = (AdvancedQuery.RuleGroup) root.rules().get(1);
    assertThat(secondAnd.combinator()).isEqualTo(ReportConstants.QueryCombinators.and);
    assertThat(secondAnd.rules()).hasSize(2);

    // First of secondAnd should be clause c
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) secondAnd.rules().getFirst(), equalsClause3);

    // Second of secondAnd should be an OR group (d, e)
    assertThat(secondAnd.rules().get(1)).isInstanceOf(AdvancedQuery.RuleGroup.class);
    AdvancedQuery.RuleGroup innerOr = (AdvancedQuery.RuleGroup) secondAnd.rules().get(1);
    assertThat(innerOr.combinator()).isEqualTo(ReportConstants.QueryCombinators.or);
    assertThat(innerOr.rules()).hasSize(2);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) innerOr.rules().getFirst(), equalsClause4);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) innerOr.rules().get(1), equalsClause5);
  }

  @Test
  void build_should_handle_nested_group_or_with_clause() {
    // (((b)) or a)
    FilterValue openParen1 = buildOperatorValue(1, "(");
    FilterValue openParen2 = buildOperatorValue(2, "(");
    FilterValue openParen3 = buildOperatorValue(3, "(");
    FilterValue equalsClause = buildClauseValue(4, "equals", "value1");
    FilterValue closeParen1 = buildOperatorValue(5, ")");
    FilterValue closeParen2 = buildOperatorValue(6, ")");
    FilterValue orOperator = buildOperatorValue(7, "or");
    FilterValue notEquals = buildClauseValue(8, "notEquals", "value2");
    FilterValue closeParen3 = buildOperatorValue(9, ")");

    filter.setFilterValues(
        List.of(
            openParen1,
            openParen2,
            openParen3,
            equalsClause,
            closeParen1,
            closeParen2,
            orOperator,
            notEquals,
            closeParen3));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    AdvancedQuery.RuleGroup root = tryBuild(builder);

    assertThat(root.combinator()).isEqualTo(ReportConstants.QueryCombinators.or);
    assertThat(root.rules()).hasSize(2);

    // first is b, second is a
    assertThat(root.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) root.rules().getFirst(), equalsClause);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) root.rules().get(1), notEquals);
  }

  @Test
  void build_should_handle_long_chain_of_and_or_combination() {
    // a and b or c and d and e or f
    FilterValue equalsClause1 = buildClauseValue(1, "equals", "a");
    FilterValue andOperator1 = buildOperatorValue(2, "and");
    FilterValue equalsClause2 = buildClauseValue(3, "equals", "b");
    FilterValue orOperator1 = buildOperatorValue(4, "or");
    FilterValue equalsClause3 = buildClauseValue(5, "equals", "c");
    FilterValue andOperator2 = buildOperatorValue(6, "and");
    FilterValue equalsClause4 = buildClauseValue(7, "equals", "d");
    FilterValue andOperator3 = buildOperatorValue(8, "and");
    FilterValue equalsClause5 = buildClauseValue(9, "equals", "e");
    FilterValue orOperator2 = buildOperatorValue(10, "or");
    FilterValue equalsClause6 = buildClauseValue(11, "equals", "f");

    filter.setFilterValues(
        List.of(
            equalsClause1,
            andOperator1,
            equalsClause2,
            orOperator1,
            equalsClause3,
            andOperator2,
            equalsClause4,
            andOperator3,
            equalsClause5,
            orOperator2,
            equalsClause6));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter.getFilterValues());
    AdvancedQuery.RuleGroup root = tryBuild(builder);

    // root should be OR with two rules: AND(a,b), OR(AND(c,d,e), f)
    assertThat(root.combinator()).isEqualTo(ReportConstants.QueryCombinators.or);
    assertThat(root.rules()).hasSize(2);

    // first is AND group - (a,b)
    assertThat(root.rules().getFirst()).isInstanceOf(AdvancedQuery.RuleGroup.class);
    AdvancedQuery.RuleGroup first = (AdvancedQuery.RuleGroup) root.rules().getFirst();
    assertThat(first.combinator()).isEqualTo(ReportConstants.QueryCombinators.and);
    assertThat(first.rules()).hasSize(2);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) first.rules().getFirst(), equalsClause1);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) first.rules().get(1), equalsClause2);

    // second is OR group - (AND(c,d,e), f)
    assertThat(root.rules().get(1)).isInstanceOf(AdvancedQuery.RuleGroup.class);
    AdvancedQuery.RuleGroup second = (AdvancedQuery.RuleGroup) root.rules().get(1);
    assertThat(second.combinator()).isEqualTo(ReportConstants.QueryCombinators.or);
    assertThat(second.rules()).hasSize(2);

    // first of OR group is AND(c,d,e)
    assertThat(second.rules().getFirst()).isInstanceOf(AdvancedQuery.RuleGroup.class);
    AdvancedQuery.RuleGroup nestedAnd = (AdvancedQuery.RuleGroup) second.rules().getFirst();
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) nestedAnd.rules().getFirst(), equalsClause3);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) nestedAnd.rules().get(1), equalsClause4);
    assertRuleMatchesClauseValue((AdvancedQuery.Rule) nestedAnd.rules().get(2), equalsClause5);

    // second of OR group is f
    assertThat(second.rules().get(1)).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule equals6Rule = (AdvancedQuery.Rule) second.rules().get(1);

    assertRuleMatchesClauseValue(equals6Rule, equalsClause6);
  }
}

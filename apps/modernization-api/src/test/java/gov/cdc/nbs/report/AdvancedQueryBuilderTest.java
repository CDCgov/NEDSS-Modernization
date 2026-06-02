package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
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

  private FilterValue buildOperatorValue(
      ReportFilter filter, Integer sequenceNumber, String operator) {
    return FilterValue.builder()
        .id(faker.number().randomNumber())
        .reportFilter(filter)
        .sequenceNumber(sequenceNumber)
        .valueType("OPERATOR")
        .operator(operator)
        .build();
  }

  private FilterValue buildClauseValue(
      ReportFilter filter, Integer sequenceNumber, String operator, String valueTxt) {
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

  private void assertRuleGroupMatchesOperatorValue(
      AdvancedQuery.RuleGroup ruleGroup, FilterValue operatorValue, Integer expectedRuleCount) {
    assertThat(ruleGroup.id()).isEqualTo(operatorValue.getId().toString());
    assertThat(ruleGroup.combinator()).isEqualTo(operatorValue.getOperator());
    assertThat(ruleGroup.rules()).hasSize(expectedRuleCount);
  }

  private void assertRuleMatchesClauseValue(AdvancedQuery.Rule rule, FilterValue clauseValue) {
    assertThat(rule.id()).isEqualTo(clauseValue.getId().toString());
    assertThat(rule.columnId()).isEqualTo(clauseValue.getColumnUid());
    assertThat(rule.operator()).isEqualTo(clauseValue.getOperator());
    assertThat(rule.value()).isEqualTo(clauseValue.getValueTxt());
  }

  ////////////////////////////////////////////////////////////////////////

  @Test
  void build_should_create_rule_group_with_single_rule() {
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue groupValue = buildOperatorValue(filter, 1, "and");
    FilterValue clauseValue = buildClauseValue(filter, 2, "equals", "test value");

    filter.setFilterValues(List.of(groupValue, clauseValue));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter);

    // Act
    AdvancedQuery.RuleGroup result = builder.build();

    // Assert
    assertRuleGroupMatchesOperatorValue(result, groupValue, 1);

    assertThat(result.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule = (AdvancedQuery.Rule) result.rules().getFirst();

    assertRuleMatchesClauseValue(rule, clauseValue);
  }

  @Test
  void build_should_create_rule_group_with_multiple_rules() {
    // Arrange: Create a ReportFilter with a group FilterValue and two clause FilterValues
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();
    FilterValue rootOrClause = buildOperatorValue(filter, 1, "or");

    FilterValue clauseValue1 = buildClauseValue(filter, 2, "contains", "value1");
    FilterValue clauseValue2 = buildClauseValue(filter, 3, "startsWith", "value2");

    filter.setFilterValues(List.of(rootOrClause, clauseValue1, clauseValue2));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter);

    // Act
    AdvancedQuery.RuleGroup result = builder.build();

    // Assert
    assertRuleGroupMatchesOperatorValue(result, rootOrClause, 2);

    assertThat(result.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule1 = (AdvancedQuery.Rule) result.rules().getFirst();
    assertRuleMatchesClauseValue(rule1, clauseValue1);

    assertThat(result.rules().get(1)).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule2 = (AdvancedQuery.Rule) result.rules().get(1);
    assertRuleMatchesClauseValue(rule2, clauseValue2);
  }

  @Test
  void build_should_create_rule_group_with_nested_rule_group() {
    // Arrange: Create a ReportFilter with a group FilterValue, a clause, an operator, and another
    // clause
    ReportFilter filter = ReportFilter.builder().report(report).filterCode(filterCode).build();

    FilterValue andValue = buildOperatorValue(filter, 1, "and");
    FilterValue clauseValue1 = buildClauseValue(filter, 2, "equals", "value1");
    FilterValue orValue = buildOperatorValue(filter, 3, "or");
    FilterValue clauseValue2 = buildClauseValue(filter, 4, "notEquals", "value2");

    filter.setFilterValues(List.of(andValue, clauseValue1, orValue, clauseValue2));

    AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter);

    // Act
    AdvancedQuery.RuleGroup rootRuleGroup = builder.build();

    // Assert
    assertRuleGroupMatchesOperatorValue(rootRuleGroup, andValue, 2);

    // First rule
    assertThat(rootRuleGroup.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule1 = (AdvancedQuery.Rule) rootRuleGroup.rules().getFirst();
    assertRuleMatchesClauseValue(rule1, clauseValue1);

    // Second is a nested RuleGroup
    assertThat(rootRuleGroup.rules().get(1)).isInstanceOf(AdvancedQuery.RuleGroup.class);
    AdvancedQuery.RuleGroup nestedGroup = (AdvancedQuery.RuleGroup) rootRuleGroup.rules().get(1);
    assertRuleGroupMatchesOperatorValue(nestedGroup, orValue, 1);

    assertThat(nestedGroup.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
    AdvancedQuery.Rule rule2 = (AdvancedQuery.Rule) nestedGroup.rules().getFirst();
    assertRuleMatchesClauseValue(rule2, clauseValue2);
  }
}

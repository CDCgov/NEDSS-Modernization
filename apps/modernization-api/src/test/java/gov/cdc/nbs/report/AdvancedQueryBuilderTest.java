package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.models.AdvancedQuery;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AdvancedQueryBuilderTest {
    private FilterValue buildOperatorValue(Long id, ReportFilter filter, Integer sequenceNumber, String operator) {
        return FilterValue.builder()
                .id(id)
                .reportFilter(filter)
                .sequenceNumber(sequenceNumber)
                .valueType("OPERATOR")
                .operator(operator)
                .build();
    }

    private FilterValue buildClauseValue(Long id, ReportFilter filter, Integer sequenceNumber, String operator, Long columnUid, String valueTxt) {
        return FilterValue.builder()
                .id(id)
                .reportFilter(filter)
                .sequenceNumber(sequenceNumber)
                .valueType("CLAUSE")
                .columnUid(columnUid)
                .operator(operator)
                .valueTxt(valueTxt)
                .build();
    }

    ////////////////////////////////////////////////////////////////////////

    @Test
    void build_should_create_rule_group_with_single_rule() {
        ReportFilter filter = ReportFilter.builder().build();

        FilterValue groupValue = buildOperatorValue(1L, filter, 1, "AND");
        FilterValue clauseValue = buildClauseValue(2L, filter, 2, "equals", 100L, "test value");

        filter.setFilterValues(List.of(groupValue, clauseValue));

        AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter);

        // Act
        AdvancedQuery.RuleGroup result = builder.build();

        // Assert
        assertThat(result.id()).isEqualTo("1");
        assertThat(result.combinator()).isEqualTo("and");
        assertThat(result.rules()).hasSize(1);
        assertThat(result.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
        AdvancedQuery.Rule rule = (AdvancedQuery.Rule) result.rules().getFirst();
        assertThat(rule.id()).isEqualTo("2");
        assertThat(rule.columnId()).isEqualTo(100L);
        assertThat(rule.operator()).isEqualTo("equals");
        assertThat(rule.value()).isEqualTo("test value");
    }

    @Test
    void build_should_create_rule_group_with_multiple_rules() {
        // Arrange: Create a ReportFilter with a group FilterValue and two clause FilterValues
        ReportFilter filter = ReportFilter.builder().build();
        FilterValue groupValue = buildOperatorValue(5L, filter, 1, "OR");

        FilterValue clauseValue1 = buildClauseValue(6L, filter, 2, "contains", 100L, "value1");
        FilterValue clauseValue2 = buildClauseValue(7L, filter, 3, "startsWith", 200L, "value2");

        filter.setFilterValues(List.of(groupValue, clauseValue1, clauseValue2));

        AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter);

        // Act
        AdvancedQuery.RuleGroup result = builder.build();

        // Assert
        assertThat(result.id()).isEqualTo("1");
        assertThat(result.combinator()).isEqualTo("or");
        assertThat(result.rules()).hasSize(2);
        assertThat(result.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
        AdvancedQuery.Rule rule1 = (AdvancedQuery.Rule) result.rules().get(0);
        assertThat(rule1.id()).isEqualTo("2");
        assertThat(rule1.columnId()).isEqualTo(100L);
        assertThat(rule1.operator()).isEqualTo("contains");
        assertThat(rule1.value()).isEqualTo("value1");

        assertThat(result.rules().get(1)).isInstanceOf(AdvancedQuery.Rule.class);
        AdvancedQuery.Rule rule2 = (AdvancedQuery.Rule) result.rules().get(1);
        assertThat(rule2.id()).isEqualTo("3");
        assertThat(rule2.columnId()).isEqualTo(200L);
        assertThat(rule2.operator()).isEqualTo("startsWith");
        assertThat(rule2.value()).isEqualTo("value2");
    }

    @Test
    void build_should_create_rule_group_with_nested_rule_group() {
        // Arrange: Create a ReportFilter with a group FilterValue, a clause, an operator, and another clause
        ReportFilter filter = ReportFilter.builder().build();

        FilterValue andValue = buildOperatorValue(1L, filter, 1, "AND");
        FilterValue clauseValue1 = buildClauseValue(2L, filter, 2, "equals", 100L, "value1");
        FilterValue orValue = buildOperatorValue(3L, filter, 3, "OR");
        FilterValue clauseValue2 = buildClauseValue(4L, filter, 4, "notEquals", 200L, "value2");

        filter.setFilterValues(List.of(andValue, clauseValue1, orValue, clauseValue2));

        AdvancedQueryBuilder builder = new AdvancedQueryBuilder(filter);

        // Act
        AdvancedQuery.RuleGroup result = builder.build();

        // Assert
        assertThat(result.id()).isEqualTo("1");
        assertThat(result.combinator()).isEqualTo("and");
        assertThat(result.rules()).hasSize(2);  // One Rule and one RuleGroup

        // First rule
        assertThat(result.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
        AdvancedQuery.Rule rule1 = (AdvancedQuery.Rule) result.rules().get(0);
        assertThat(rule1.id()).isEqualTo("2");
        assertThat(rule1.columnId()).isEqualTo(100L);
        assertThat(rule1.operator()).isEqualTo("equals");
        assertThat(rule1.value()).isEqualTo("value1");

        // Second is a nested RuleGroup
        assertThat(result.rules().get(1)).isInstanceOf(AdvancedQuery.RuleGroup.class);
        AdvancedQuery.RuleGroup nestedGroup = (AdvancedQuery.RuleGroup) result.rules().get(1);
        assertThat(nestedGroup.id()).isEqualTo("3");
        assertThat(nestedGroup.combinator()).isEqualTo("OPERATOR");  // As per code, uses valueType directly
        assertThat(nestedGroup.rules()).hasSize(1);
        assertThat(nestedGroup.rules().getFirst()).isInstanceOf(AdvancedQuery.Rule.class);
        AdvancedQuery.Rule rule2 = (AdvancedQuery.Rule) nestedGroup.rules().get(0);
        assertThat(rule2.id()).isEqualTo("4");
        assertThat(rule2.columnId()).isEqualTo(200L);
        assertThat(rule2.operator()).isEqualTo("notEquals");
        assertThat(rule2.value()).isEqualTo("value2");
    }
}
package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.GeneratedId;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdvancedFilterRequest;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FilterValueMapperTest {

  @Mock private IdGeneratorService idGenerator;
  @InjectMocks private FilterValueMapper mapper;

  private ReportFilter mockReportFilter;
  private Long generatedId;

  @BeforeEach
  void setup() {
    mockReportFilter = mock(ReportFilter.class);
    generatedId = 100L;

    GeneratedId mockValidId = mock(GeneratedId.class);
    when(mockValidId.getId()).thenReturn(generatedId);
    when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS)).thenReturn(mockValidId);
  }

  @Nested
  class FromBasicFilterRequest {

    @Test
    void fromBasicFilterRequest_should_create_filter_values_for_single_value() {
      List<String> values = List.of("testValue");
      BasicFilterRequest request = new BasicFilterRequest(1L, values, false);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(1);
      FilterValue filterValue = result.get(0);
      assertThat(filterValue.getId()).isEqualTo(generatedId);
      assertThat(filterValue.getReportFilter()).isEqualTo(mockReportFilter);
      assertThat(filterValue.getValueType()).isEqualTo(ReportConstants.BASIC_FILTER_VALUE_TYPE);
      assertThat(filterValue.getValueTxt()).isEqualTo("testValue");
    }

    @Test
    void fromBasicFilterRequest_should_create_filter_values_for_multiple_values() {
      List<String> values = List.of("value1", "value2", "value3");
      BasicFilterRequest request = new BasicFilterRequest(1L, values, false);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(3);
      assertThat(result)
          .allSatisfy(
              fv -> {
                assertThat(fv.getId()).isEqualTo(generatedId);
                assertThat(fv.getReportFilter()).isEqualTo(mockReportFilter);
                assertThat(fv.getValueType()).isEqualTo(ReportConstants.BASIC_FILTER_VALUE_TYPE);
              });
      assertThat(result.get(0).getValueTxt()).isEqualTo("value1");
      assertThat(result.get(1).getValueTxt()).isEqualTo("value2");
      assertThat(result.get(2).getValueTxt()).isEqualTo("value3");
    }

    @Test
    void fromBasicFilterRequest_should_create_filter_values_with_empty_strings() {
      List<String> values = List.of("", "value", "");
      BasicFilterRequest request = new BasicFilterRequest(1L, values, false);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(3);
      assertThat(result.get(0).getValueTxt()).isEmpty();
      assertThat(result.get(1).getValueTxt()).isEqualTo("value");
      assertThat(result.get(2).getValueTxt()).isEmpty();
    }

    @Test
    void fromBasicFilterRequest_should_create_filter_values_with_special_characters() {
      List<String> values = List.of("value@123", "test-value", "value_with_underscore");
      BasicFilterRequest request = new BasicFilterRequest(1L, values, false);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(3);
      assertThat(result.get(0).getValueTxt()).isEqualTo("value@123");
      assertThat(result.get(1).getValueTxt()).isEqualTo("test-value");
      assertThat(result.get(2).getValueTxt()).isEqualTo("value_with_underscore");
    }

    @Test
    void fromBasicFilterRequest_should_generate_unique_ids_for_each_value() {
      List<String> values = List.of("value1", "value2");
      BasicFilterRequest request = new BasicFilterRequest(1L, values, false);
      long id1 = 100L;
      long id2 = 101L;

      GeneratedId mockValidId1 = mock(GeneratedId.class);
      GeneratedId mockValidId2 = mock(GeneratedId.class);
      when(mockValidId1.getId()).thenReturn(id1);
      when(mockValidId2.getId()).thenReturn(id2);

      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(mockValidId1)
          .thenReturn(mockValidId2);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(2);
      assertThat(result.get(0).getId()).isEqualTo(id1);
      assertThat(result.get(1).getId()).isEqualTo(id2);
    }

    @Test
    void fromBasicFilterRequest_should_respect_includeNulls_flag() {
      List<String> values = List.of("value1");
      BasicFilterRequest requestWithoutNulls = new BasicFilterRequest(1L, values, false);
      BasicFilterRequest requestWithNulls = new BasicFilterRequest(1L, values, true);

      List<FilterValue> result1 =
          mapper.fromBasicFilterRequest(mockReportFilter, requestWithoutNulls);
      List<FilterValue> result2 = mapper.fromBasicFilterRequest(mockReportFilter, requestWithNulls);

      assertThat(result1).hasSize(1);
      assertThat(result2).hasSize(1);
    }

    @Test
    void fromBasicFilterRequest_should_handle_empty_list() {
      List<String> values = new ArrayList<>();
      BasicFilterRequest request = new BasicFilterRequest(1L, values, false);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).isEmpty();
    }

    @Test
    void fromBasicFilterRequest_should_handle_very_long_values() {
      String longValue = "a".repeat(1000);
      List<String> values = List.of(longValue);
      BasicFilterRequest request = new BasicFilterRequest(1L, values, false);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(1);
      assertThat(result.get(0).getValueTxt()).isEqualTo(longValue);
    }
  }

  @Nested
  class FromAdvancedFilterRequest {

    @Test
    void fromAdvancedFilterRequest_should_create_filter_values_with_single_rule() {
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(4);
      assertThat(result.get(0).getOperator()).isEqualTo("(");
      assertThat(result.get(1).getValueType())
          .isEqualTo(ReportConstants.AdvancedFilterValueType.CLAUSE.toString());
      assertThat(result.get(2).getOperator()).isEqualTo(")");
    }

    @Test
    void fromAdvancedFilterRequest_should_create_filter_values_with_multiple_rules() {
      AdvancedQuery.Rule rule1 = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.Rule rule2 = new AdvancedQuery.Rule("rule2", 2L, "NOT_EQUALS", "value2");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule1, rule2));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(6);
      assertThat(result.get(0).getOperator()).isEqualTo("(");
      assertThat(result.get(1).getValueType())
          .isEqualTo(ReportConstants.AdvancedFilterValueType.CLAUSE.toString());
      assertThat(result.get(2).getValueType())
          .isEqualTo(ReportConstants.AdvancedFilterValueType.OPERATOR.toString());
      assertThat(result.get(3).getValueType())
          .isEqualTo(ReportConstants.AdvancedFilterValueType.CLAUSE.toString());
      assertThat(result.get(4).getOperator()).isEqualTo(")");
    }

    @Test
    void fromAdvancedFilterRequest_should_set_correct_sequence_numbers() {
      AdvancedQuery.Rule rule1 = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.Rule rule2 = new AdvancedQuery.Rule("rule2", 2L, "EQUALS", "value2");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule1, rule2));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result.get(1).getSequenceNumber()).isEqualTo(1);
      assertThat(result.get(2).getSequenceNumber()).isEqualTo(2);
      assertThat(result.get(3).getSequenceNumber()).isEqualTo(3);
    }

    @Test
    void fromAdvancedFilterRequest_should_set_clause_properties() {
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 99L, "CONTAINS", "testValue");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      FilterValue clause = result.get(1);
      assertThat(clause.getValueType())
          .isEqualTo(ReportConstants.AdvancedFilterValueType.CLAUSE.toString());
      assertThat(clause.getColumnUid()).isEqualTo(99L);
      assertThat(clause.getOperator()).isEqualTo("CONTAINS");
      assertThat(clause.getValueTxt()).isEqualTo("testValue");
    }

    @Test
    void fromAdvancedFilterRequest_should_set_operator_properties() {
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      FilterValue operator = result.get(0);
      assertThat(operator.getValueType())
          .isEqualTo(ReportConstants.AdvancedFilterValueType.OPERATOR.toString());
      assertThat(operator.getOperator()).isEqualTo("(");
    }

    @Test
    void fromAdvancedFilterRequest_should_generate_unique_ids() {
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).allSatisfy(fv -> assertThat(fv.getId()).isEqualTo(generatedId));
    }

    @Test
    void fromAdvancedFilterRequest_should_handle_nested_rule_groups() {
      AdvancedQuery.Rule rule1 = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.Rule rule2 = new AdvancedQuery.Rule("rule2", 2L, "EQUALS", "value2");

      AdvancedQuery.RuleGroup nestedGroup =
          new AdvancedQuery.RuleGroup(
              "nested", ReportConstants.QueryCombinators.OR, List.of(rule2));
      AdvancedQuery.RuleGroup outerGroup =
          new AdvancedQuery.RuleGroup(
              "outer",
              ReportConstants.QueryCombinators.AND,
              new ArrayList<>(List.of(rule1, nestedGroup)));

      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, outerGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).isNotEmpty();
      assertThat(result).anySatisfy(fv -> assertThat(fv.getOperator()).isEqualTo("("));
      assertThat(result).anySatisfy(fv -> assertThat(fv.getOperator()).isEqualTo(")"));
    }

    @Test
    void fromAdvancedFilterRequest_should_handle_or_combinator() {
      AdvancedQuery.Rule rule1 = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.Rule rule2 = new AdvancedQuery.Rule("rule2", 2L, "EQUALS", "value2");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.OR, List.of(rule1, rule2));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(6);
      assertThat(result.get(2).getOperator()).isNotNull();
    }

    @Test
    void fromAdvancedFilterRequest_should_handle_single_rule_group_with_no_operator() {
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(4);
    }

    @Test
    void fromAdvancedFilterRequest_should_have_matching_parentheses() {
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      long openParens = result.stream().filter(fv -> "(".equals(fv.getOperator())).count();
      long closeParens = result.stream().filter(fv -> ")".equals(fv.getOperator())).count();

      assertThat(openParens).isEqualTo(closeParens);
    }

    @Test
    void fromAdvancedFilterRequest_should_set_report_filter_for_all_values() {
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result)
          .allSatisfy(fv -> assertThat(fv.getReportFilter()).isEqualTo(mockReportFilter));
    }

    @Test
    void fromAdvancedFilterRequest_should_increment_sequence_numbers_across_calls() {
      AdvancedQuery.Rule rule1 = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup1 =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule1));
      AdvancedFilterRequest request1 = new AdvancedFilterRequest(1L, ruleGroup1);

      List<FilterValue> result1 = mapper.fromAdvancedFilterRequest(mockReportFilter, request1);
      int lastSequence1 = result1.stream().mapToInt(FilterValue::getSequenceNumber).max().orElse(0);

      AdvancedQuery.Rule rule2 = new AdvancedQuery.Rule("rule2", 2L, "EQUALS", "value2");
      AdvancedQuery.RuleGroup ruleGroup2 =
          new AdvancedQuery.RuleGroup(
              "group2", ReportConstants.QueryCombinators.AND, List.of(rule2));
      AdvancedFilterRequest request2 = new AdvancedFilterRequest(1L, ruleGroup2);

      List<FilterValue> result2 = mapper.fromAdvancedFilterRequest(mockReportFilter, request2);
      int firstSequence2 =
          result2.stream().mapToInt(FilterValue::getSequenceNumber).min().orElse(0);

      assertThat(firstSequence2).isGreaterThan(lastSequence1);
    }

    @Test
    void fromAdvancedFilterRequest_should_have_non_zero_sequence_numbers() {
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result)
          .filteredOn(fv -> fv.getSequenceNumber() != null)
          .allSatisfy(fv -> assertThat(fv.getSequenceNumber()).isGreaterThan(0));
    }

    @Test
    void fromAdvancedFilterRequest_should_handle_multiple_nested_groups() {
      AdvancedQuery.Rule rule1 = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.Rule rule2 = new AdvancedQuery.Rule("rule2", 2L, "EQUALS", "value2");

      AdvancedQuery.RuleGroup nestedGroup1 =
          new AdvancedQuery.RuleGroup(
              "nested1", ReportConstants.QueryCombinators.AND, List.of(rule1));
      AdvancedQuery.RuleGroup nestedGroup2 =
          new AdvancedQuery.RuleGroup(
              "nested2", ReportConstants.QueryCombinators.OR, List.of(rule2));

      AdvancedQuery.RuleGroup outerGroup =
          new AdvancedQuery.RuleGroup(
              "outer",
              ReportConstants.QueryCombinators.AND,
              new ArrayList<>(List.of(nestedGroup1, nestedGroup2)));

      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, outerGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).isNotEmpty();
    }
  }
}

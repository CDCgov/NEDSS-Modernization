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
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    Mockito.lenient().when(mockValidId.getId()).thenReturn(generatedId);
    Mockito.lenient()
        .when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
        .thenReturn(mockValidId);
  }

  @Nested
  class FromBasicFilterRequest {

    @Test
    void fromBasicFilterRequest_should_create_filter_values_for_single_value() {
      List<String> values = List.of("testValue");
      BasicFilterRequest request = new BasicFilterRequest(1L, values, false);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(1);
      FilterValue filterValue = result.getFirst();

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

      assertThat(result)
          .hasSize(3)
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
    void fromBasicFilterRequest_should_handle_empty_list() {
      List<String> values = new ArrayList<>();
      BasicFilterRequest request = new BasicFilterRequest(1L, values, false);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).isEmpty();
    }
  }

  @Nested
  class FromAdvancedFilterRequest {

    private void assertMatchingClauseValue(
        FilterValue filterValue, AdvancedQuery.Rule rule, int sequenceNumber) {
      assertThat(filterValue.getValueType())
          .isEqualTo(ReportConstants.AdvancedFilterValueType.CLAUSE.toString());
      assertThat(filterValue.getColumnUid()).isEqualTo(rule.columnId());
      assertThat(filterValue.getOperator()).isEqualTo(rule.operator());
      assertThat(filterValue.getValueTxt()).isEqualTo(rule.value());
      assertThat(filterValue.getSequenceNumber()).isEqualTo(sequenceNumber);
    }

    private void assertMatchingOperatorValue(
        FilterValue filterValue, String operator, int sequenceNumber) {
      assertThat(filterValue.getValueType())
          .isEqualTo(ReportConstants.AdvancedFilterValueType.OPERATOR.toString());
      assertThat(filterValue.getOperator()).isEqualTo(operator);
      assertThat(filterValue.getSequenceNumber()).isEqualTo(sequenceNumber);
    }

    @Test
    void fromAdvancedFilterRequest_should_create_filter_values_with_single_rule() {
      AdvancedQuery.Rule rule =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(), 1L, ReportConstants.Operator.EQ.toString(), "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              UUID.randomUUID().toString(), ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(3);

      assertMatchingOperatorValue(result.getFirst(), "(", 1);
      assertMatchingClauseValue(result.get(1), rule, 2);
      assertMatchingOperatorValue(result.getLast(), ")", 3);
    }

    @Test
    void fromAdvancedFilterRequest_should_create_filter_values_with_multiple_rules() {
      AdvancedQuery.Rule rule1 =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(), 1L, ReportConstants.Operator.EQ.toString(), "value1");
      AdvancedQuery.Rule rule2 =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(), 2L, ReportConstants.Operator.NE.toString(), "value2");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              UUID.randomUUID().toString(),
              ReportConstants.QueryCombinators.AND,
              List.of(rule1, rule2));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(5);

      assertMatchingOperatorValue(result.getFirst(), "(", 1);

      assertMatchingClauseValue(result.get(1), rule1, 2);
      assertMatchingOperatorValue(
          result.get(2), ReportConstants.QueryCombinators.AND.toString(), 3);
      assertMatchingClauseValue(result.get(3), rule2, 4);

      assertMatchingOperatorValue(result.getLast(), ")", 5);
    }

    @Test
    void fromAdvancedFilterRequest_should_generate_unique_ids() {
      AdvancedQuery.Rule rule =
          new AdvancedQuery.Rule(UUID.randomUUID().toString(), 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(3).allSatisfy(fv -> assertThat(fv.getId()).isEqualTo(generatedId));
    }

    @Test
    void fromAdvancedFilterRequest_should_handle_nested_rule_groups() {
      AdvancedQuery.Rule rule1 =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(), 1L, ReportConstants.Operator.EQ.toString(), "value1");
      AdvancedQuery.Rule rule2 =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(), 2L, ReportConstants.Operator.NE.toString(), "value2");
      AdvancedQuery.Rule rule3 =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(), 3L, ReportConstants.Operator.NE.toString(), "value3");

      AdvancedQuery.RuleGroup nestedGroup =
          new AdvancedQuery.RuleGroup(
              UUID.randomUUID().toString(),
              ReportConstants.QueryCombinators.OR,
              List.of(rule2, rule3));

      AdvancedQuery.RuleGroup rootGroup =
          new AdvancedQuery.RuleGroup(
              UUID.randomUUID().toString(),
              ReportConstants.QueryCombinators.AND,
              new ArrayList<>(List.of(rule1, nestedGroup)));

      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, rootGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(9);

      assertMatchingOperatorValue(result.getFirst(), "(", 1);

      assertMatchingClauseValue(result.get(1), rule1, 2);
      assertMatchingOperatorValue(
          result.get(2), ReportConstants.QueryCombinators.AND.toString(), 3);
      assertMatchingOperatorValue(result.get(3), "(", 4);
      assertMatchingClauseValue(result.get(4), rule2, 5);
      assertMatchingOperatorValue(result.get(5), ReportConstants.QueryCombinators.OR.toString(), 6);
      assertMatchingClauseValue(result.get(6), rule3, 7);
      assertMatchingOperatorValue(result.get(7), ")", 8);

      assertThat(result.getLast()).isEqualTo(")");
    }
  }

  @Nested
  class DuplicateFilterValue {

    @Test
    void duplicate_should_generate_new_id() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(1);
      originalValue.setValueType("CLAUSE");
      originalValue.setColumnUid(50L);
      originalValue.setOperator("EQ");
      originalValue.setValueTxt("test");

      long newId = 200L;
      GeneratedId newGeneratedId = mock(GeneratedId.class);
      when(newGeneratedId.getId()).thenReturn(newId);
      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(newGeneratedId);

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getId()).isEqualTo(newId);
      assertThat(duplicatedValue.getId()).isNotEqualTo(originalValue.getId());
    }

    @Test
    void duplicate_should_preserve_report_filter() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(1);
      originalValue.setValueType("CLAUSE");
      originalValue.setColumnUid(50L);
      originalValue.setOperator("EQ");
      originalValue.setValueTxt("test");

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getReportFilter()).isEqualTo(mockReportFilter);
    }

    @Test
    void duplicate_should_preserve_sequence_number() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(5);
      originalValue.setValueType("CLAUSE");
      originalValue.setColumnUid(50L);
      originalValue.setOperator("EQ");
      originalValue.setValueTxt("test");

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getSequenceNumber()).isEqualTo(5);
    }

    @Test
    void duplicate_should_preserve_value_type() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(1);
      originalValue.setValueType("OPERATOR");
      originalValue.setColumnUid(50L);
      originalValue.setOperator("AND");
      originalValue.setValueTxt("test");

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getValueType()).isEqualTo("OPERATOR");
    }

    @Test
    void duplicate_should_preserve_column_uid() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(1);
      originalValue.setValueType("CLAUSE");
      originalValue.setColumnUid(999L);
      originalValue.setOperator("EQ");
      originalValue.setValueTxt("test");

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getColumnUid()).isEqualTo(999L);
    }

    @Test
    void duplicate_should_preserve_operator() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(1);
      originalValue.setValueType("CLAUSE");
      originalValue.setColumnUid(50L);
      originalValue.setOperator("NE");
      originalValue.setValueTxt("test");

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getOperator()).isEqualTo("NE");
    }

    @Test
    void duplicate_should_preserve_value_text() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(1);
      originalValue.setValueType("CLAUSE");
      originalValue.setColumnUid(50L);
      originalValue.setOperator("EQ");
      originalValue.setValueTxt("specific_test_value");

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getValueTxt()).isEqualTo("specific_test_value");
    }

    @Test
    void duplicate_should_preserve_all_fields_when_fully_populated() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(3);
      originalValue.setValueType("CLAUSE");
      originalValue.setColumnUid(750L);
      originalValue.setOperator("GT");
      originalValue.setValueTxt("complex_value");

      long newId = 200L;
      GeneratedId newGeneratedId = mock(GeneratedId.class);
      when(newGeneratedId.getId()).thenReturn(newId);
      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(newGeneratedId);

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue).isNotNull();
      assertThat(duplicatedValue.getId()).isEqualTo(newId);
      assertThat(duplicatedValue.getReportFilter()).isEqualTo(mockReportFilter);
      assertThat(duplicatedValue.getSequenceNumber()).isEqualTo(3);
      assertThat(duplicatedValue.getValueType()).isEqualTo("CLAUSE");
      assertThat(duplicatedValue.getColumnUid()).isEqualTo(750L);
      assertThat(duplicatedValue.getOperator()).isEqualTo("GT");
      assertThat(duplicatedValue.getValueTxt()).isEqualTo("complex_value");
    }

    @Test
    void duplicate_should_handle_null_sequence_number() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(null);
      originalValue.setValueType("OPERATOR");
      originalValue.setOperator("(");

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getSequenceNumber()).isNull();
    }

    @Test
    void duplicate_should_handle_null_column_uid() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(1);
      originalValue.setValueType("OPERATOR");
      originalValue.setColumnUid(null);
      originalValue.setOperator("(");

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getColumnUid()).isNull();
    }

    @Test
    void duplicate_should_handle_null_operator() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(1);
      originalValue.setValueType("CLAUSE");
      originalValue.setColumnUid(50L);
      originalValue.setOperator(null);
      originalValue.setValueTxt("test");

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getOperator()).isNull();
    }

    @Test
    void duplicate_should_handle_null_value_text() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(1);
      originalValue.setValueType("OPERATOR");
      originalValue.setOperator("(");
      originalValue.setValueTxt(null);

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getValueTxt()).isNull();
    }

    @Test
    void duplicate_should_call_id_generator_exactly_once() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);

      mapper.duplicate(originalValue);

      verify(idGenerator).getNextValidId(IdGeneratorService.EntityType.NBS);
    }

    @Test
    void duplicate_should_generate_unique_ids_for_multiple_values() {
      FilterValue originalValue1 = mock(FilterValue.class);
      originalValue1.setId(100L);
      originalValue1.setReportFilter(mockReportFilter);

      FilterValue originalValue2 = mock(FilterValue.class);
      originalValue2.setId(101L);
      originalValue2.setReportFilter(mockReportFilter);

      long newId1 = 200L;
      long newId2 = 201L;

      GeneratedId newGeneratedId1 = mock(GeneratedId.class);
      GeneratedId newGeneratedId2 = mock(GeneratedId.class);
      when(newGeneratedId1.getId()).thenReturn(newId1);
      when(newGeneratedId2.getId()).thenReturn(newId2);

      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(newGeneratedId1)
          .thenReturn(newGeneratedId2);

      FilterValue duplicatedValue1 = mapper.duplicate(originalValue1);
      FilterValue duplicatedValue2 = mapper.duplicate(originalValue2);

      assertThat(duplicatedValue1.getId()).isEqualTo(newId1);
      assertThat(duplicatedValue2.getId()).isEqualTo(newId2);
      assertThat(duplicatedValue1.getId()).isNotEqualTo(duplicatedValue2.getId());
    }

    @Test
    void duplicate_should_handle_empty_value_text() {
      FilterValue originalValue = mock(FilterValue.class);
      originalValue.setId(100L);
      originalValue.setReportFilter(mockReportFilter);
      originalValue.setSequenceNumber(1);
      originalValue.setValueType("CLAUSE");
      originalValue.setColumnUid(50L);
      originalValue.setOperator("EQ");
      originalValue.setValueTxt("");

      FilterValue duplicatedValue = mapper.duplicate(originalValue);

      assertThat(duplicatedValue.getValueTxt()).isEmpty();
    }
  }
}

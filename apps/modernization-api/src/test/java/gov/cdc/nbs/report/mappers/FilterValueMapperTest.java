package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.GeneratedId;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdvancedFilterRequest;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.time.EffectiveTime;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FilterValueMapperTest {

  @Spy private Clock clock = Clock.fixed(Instant.ofEpochMilli(100000), ZoneId.systemDefault());
  @Mock private IdGeneratorService idGenerator;
  @InjectMocks private FilterValueMapper mapper;

  private ReportFilter mockReportFilter;
  private Long generatedId;

  @BeforeEach
  void setup() {
    mockReportFilter = mock(ReportFilter.class);
    long reportFilterId = 832L;
    Mockito.lenient().when(mockReportFilter.getId()).thenReturn(reportFilterId);

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
      BasicFilterRequest request = new BasicFilterRequest(mockReportFilter.getId(), values, false);

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
      BasicFilterRequest request = new BasicFilterRequest(mockReportFilter.getId(), values, false);

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
    void fromBasicFilterRequest_should_handle_includeNulls_parameter() {
      List<String> values = List.of("value1");
      BasicFilterRequest request = new BasicFilterRequest(mockReportFilter.getId(), values, true);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result)
          .hasSize(2)
          .allSatisfy(
              fv -> {
                assertThat(fv.getId()).isNotNull();
                assertThat(fv.getReportFilter()).isEqualTo(mockReportFilter);
                assertThat(fv.getValueType()).isEqualTo(ReportConstants.BASIC_FILTER_VALUE_TYPE);
              });

      assertThat(result.getLast().getOperator())
          .isEqualTo(ReportConstants.BASIC_FILTER_ALLOW_NULLS_OP);
    }

    @Test
    void fromBasicFilterRequest_should_create_filter_values_with_empty_strings() {
      List<String> values = List.of("", "value", "");
      BasicFilterRequest request = new BasicFilterRequest(mockReportFilter.getId(), values, false);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(3);
      assertThat(result.get(0).getValueTxt()).isEmpty();
      assertThat(result.get(1).getValueTxt()).isEqualTo("value");
      assertThat(result.get(2).getValueTxt()).isEmpty();
    }

    @Test
    void fromBasicFilterRequest_should_generate_unique_ids_for_each_value() {
      List<String> values = List.of("value1", "value2");
      BasicFilterRequest request = new BasicFilterRequest(mockReportFilter.getId(), values, false);
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
    void fromBasicFilterRequest_should_handle_empty_values_list() {
      List<String> values = new ArrayList<>();
      BasicFilterRequest request = new BasicFilterRequest(mockReportFilter.getId(), values, false);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).isEmpty();
    }

    @Test
    void fromBasicFilterRequest_should_set_relevant_fields_for_bas_txt_filters() {
      String searchText = "basic_text_search_value";

      FilterCode basTxtFilterCode =
          FilterCode.builder()
              .id(48930L)
              .codeTable("NONE")
              .descTxt("Basic Text Filter")
              .effectiveTime(
                  new EffectiveTime(
                      LocalDateTime.now(clock).minusMonths(4),
                      LocalDateTime.now(clock).plusYears(3)))
              .code("TXT_01")
              .filterType("BAS_TXT")
              .filterName("Basic Text Filter")
              .status(new Status(Status.ACTIVE_CODE, LocalDateTime.now(clock)))
              .build();

      List<String> values = List.of(searchText);
      Mockito.lenient().when(mockReportFilter.getFilterCode()).thenReturn(basTxtFilterCode);

      BasicFilterRequest request = new BasicFilterRequest(mockReportFilter.getId(), values, false);

      List<FilterValue> result = mapper.fromBasicFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(1);
      FilterValue filterValue = result.getFirst();

      assertThat(filterValue.getId()).isEqualTo(generatedId);
      assertThat(filterValue.getReportFilter()).isEqualTo(mockReportFilter);
      assertThat(filterValue.getValueType()).isEqualTo(ReportConstants.BASIC_FILTER_VALUE_TYPE);
      assertThat(filterValue.getValueTxt()).isEqualTo(searchText);
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

      assertMatchingOperatorValue(result.getLast(), ")", 9);
    }

    @Test
    void fromAdvancedFilterRequest_should_set_value_txt_to_empty_string_for_not_null_operator() {
      AdvancedQuery.Rule ruleWithEmptyStringVal =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(), 1L, ReportConstants.Operator.NN.toString(), "");

      AdvancedQuery.Rule ruleWithNullVal =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(), 1L, ReportConstants.Operator.NN.toString(), null);

      AdvancedQuery.Rule ruleWithTextVal =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(),
              1L,
              ReportConstants.Operator.NN.toString(),
              "random Text");

      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              UUID.randomUUID().toString(),
              ReportConstants.QueryCombinators.AND,
              List.of(ruleWithEmptyStringVal, ruleWithNullVal, ruleWithTextVal));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(7);

      assertMatchingOperatorValue(result.getFirst(), "(", 1);

      assertThat(result.get(1).getValueTxt()).isEmpty();
      assertThat(result.get(3).getValueTxt()).isEmpty();
      assertThat(result.get(5).getValueTxt()).isEmpty();

      assertMatchingOperatorValue(result.getLast(), ")", 7);
    }

    @Test
    void fromAdvancedFilterRequest_should_set_value_txt_to_empty_string_for_is_null_operator() {
      AdvancedQuery.Rule ruleWithEmptyStringVal =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(), 1L, ReportConstants.Operator.IN.toString(), "");

      AdvancedQuery.Rule ruleWithNullVal =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(), 1L, ReportConstants.Operator.IN.toString(), null);

      AdvancedQuery.Rule ruleWithTextVal =
          new AdvancedQuery.Rule(
              UUID.randomUUID().toString(),
              1L,
              ReportConstants.Operator.IN.toString(),
              "random Text");

      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              UUID.randomUUID().toString(),
              ReportConstants.QueryCombinators.AND,
              List.of(ruleWithEmptyStringVal, ruleWithNullVal, ruleWithTextVal));
      AdvancedFilterRequest request = new AdvancedFilterRequest(1L, ruleGroup);

      List<FilterValue> result = mapper.fromAdvancedFilterRequest(mockReportFilter, request);

      assertThat(result).hasSize(7);

      assertMatchingOperatorValue(result.getFirst(), "(", 1);

      assertThat(result.get(1).getValueTxt()).isEmpty();
      assertThat(result.get(3).getValueTxt()).isEmpty();
      assertThat(result.get(5).getValueTxt()).isEmpty();

      assertMatchingOperatorValue(result.getLast(), ")", 7);
    }
  }
}

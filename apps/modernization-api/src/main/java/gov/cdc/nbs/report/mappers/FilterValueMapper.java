package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdvancedFilterRequest;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class FilterValueMapper {
  private final IdGeneratorService idGenerator;

  public FilterValueMapper(IdGeneratorService idGenerator) {
    this.idGenerator = idGenerator;
  }

  public List<FilterValue> fromBasicFilterRequest(
      ReportFilter basicFilter, BasicFilterRequest request) {
    List<FilterValue> basicFilterValues = new ArrayList<>();
    String operator = request.includeNulls() ? ReportConstants.BASIC_FILTER_ALLOW_NULLS_OP : null;

    if (request.values().isEmpty()) {
      if (!request.includeNulls()) return basicFilterValues;
      else {
        basicFilterValues.add(
            FilterValue.builder()
                .id(generateFilterValueId())
                .reportFilter(basicFilter)
                .operator(ReportConstants.BASIC_FILTER_ALLOW_NULLS_OP)
                .valueType(ReportConstants.BASIC_FILTER_ALLOW_NULLS_VALUE_TYPE)
                .valueTxt("")
                .build());
      }
    } else if (ReportConstants.BAS_TIME_RANGE_TYPES.contains(
        basicFilter.getFilterCode().getFilterType())) {
      if (request.values().size() != 2) {
        throw new IllegalArgumentException(
            "Time range filter must have exactly two values: start and end");
      }

      FilterValue beginRangeVal =
          FilterValue.builder()
              .id(generateFilterValueId())
              .reportFilter(basicFilter)
              .valueType("BEGIN_RANGE")
              .operator(operator)
              .valueTxt(request.values().getFirst())
              .build();

      FilterValue endRangeVal =
          FilterValue.builder()
              .id(generateFilterValueId())
              .reportFilter(basicFilter)
              .valueType("END_RANGE")
              .operator(operator)
              .valueTxt(request.values().getLast())
              .build();

      basicFilterValues.addAll(Arrays.asList(beginRangeVal, endRangeVal));
    } else {
      basicFilterValues.addAll(
          request.values().stream()
              .map(
                  value ->
                      FilterValue.builder()
                          .id(generateFilterValueId())
                          .reportFilter(basicFilter)
                          .valueType(ReportConstants.BASIC_FILTER_VALUE_TYPE)
                          .valueTxt(value == null ? "" : value)
                          .operator(operator)
                          .build())
              .collect(Collectors.toCollection(ArrayList::new)));
    }

    return basicFilterValues;
  }

  public List<FilterValue> fromAdvancedFilterRequest(
      ReportFilter advancedFilter, AdvancedFilterRequest request) {
    List<FilterValue> filterValues = mapRuleGroupToFilterValues(advancedFilter, request.value());

    for (int i = 0; i < filterValues.size(); i++) {
      FilterValue filterValue = filterValues.get(i);
      filterValue.setSequenceNumber(i + 1);
    }

    return filterValues;
  }

  // Private Methods //////////////////////////////////////////

  private List<FilterValue> mapRuleGroupToFilterValues(
      ReportFilter advancedFilter, AdvancedQuery.RuleGroup ruleGroup) {
    List<FilterValue> filterValues = new ArrayList<>();

    FilterValue openParen = buildOpenParenFilterValue(advancedFilter);
    filterValues.add(openParen);

    for (int i = 0; i < ruleGroup.rules().size(); i++) {
      AdvancedQuery rule = ruleGroup.rules().get(i);

      if (rule instanceof AdvancedQuery.Rule r) {
        FilterValue clause = buildClauseFilterValue(advancedFilter, r);
        filterValues.add(clause);

        if (i < ruleGroup.rules().size() - 1) {
          FilterValue operator =
              buildOperatorFilterValue(advancedFilter, ruleGroup.combinator().toString());
          filterValues.add(operator);
        }
      } else if (rule instanceof AdvancedQuery.RuleGroup r) {
        List<FilterValue> ruleGroupValues = mapRuleGroupToFilterValues(advancedFilter, r);
        filterValues.addAll(ruleGroupValues);
      }
    }

    FilterValue closeParen = buildCloseParenFilterValue(advancedFilter);
    filterValues.add(closeParen);

    return filterValues;
  }

  private FilterValue buildOpenParenFilterValue(ReportFilter advancedFilter) {
    return buildOperatorFilterValue(advancedFilter, "(");
  }

  private FilterValue buildCloseParenFilterValue(ReportFilter advancedFilter) {
    return buildOperatorFilterValue(advancedFilter, ")");
  }

  private FilterValue buildClauseFilterValue(ReportFilter advancedFilter, AdvancedQuery.Rule rule) {
    String valueTxt = rule.value();

    if (Arrays.asList(
            ReportConstants.Operator.IN.toString(), ReportConstants.Operator.NN.toString())
        .contains(rule.operator())) {
      valueTxt = "";
    }

    return FilterValue.builder()
        .id(generateFilterValueId())
        .valueType(ReportConstants.AdvancedFilterValueType.CLAUSE.toString())
        .reportFilter(advancedFilter)
        .columnUid(rule.columnId())
        .operator(rule.operator())
        .valueTxt(valueTxt)
        .build();
  }

  private FilterValue buildOperatorFilterValue(ReportFilter advancedFilter, String operator) {
    return FilterValue.builder()
        .id(generateFilterValueId())
        .reportFilter(advancedFilter)
        .valueType(ReportConstants.AdvancedFilterValueType.OPERATOR.toString())
        .operator(operator)
        .build();
  }

  private Long generateFilterValueId() {
    var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }
}

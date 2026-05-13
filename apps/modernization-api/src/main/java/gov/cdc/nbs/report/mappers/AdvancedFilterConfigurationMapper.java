package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdvancedFilterConfiguration;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.FilterType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvancedFilterConfigurationMapper {
  private static final List<String> KNOWN_OPERATORS = Arrays.asList(
          "GE",
          "LE",
          "GT",
          "LT",
          "BW",
          "CO",
          "SW",
          "EQ",
          "NE",
          "IN",
          "NN"
  );

  private AdvancedFilterConfigurationMapper() {
  }

  public static AdvancedFilterConfiguration fromReportFilter(ReportFilter filter) {
    FilterType filterType = FilterTypeMapper.fromFilterCode(filter.getFilterCode());

    if (!filterType.filterType().equals(ReportConstants.ADV_FILTER_TYPE)) {
      throw new IllegalArgumentException(
              "Cannot create advanced filter from non where clause builder filter");
    }

    List<FilterValue> filterValues = filter.getFilterValues();
    FilterValue filterValue = filterValues.removeFirst();

    AdvancedQuery.RuleGroup defaultValue = new AdvancedQuery.RuleGroup(filterValue.getId().toString(), filterValue.getValueType(), buildRules(filterValues, 0));

    return new AdvancedFilterConfiguration(filter.getId(), defaultValue);
  }

  private static List<AdvancedQuery> buildRules(List<FilterValue> filterValues, int index) {
    List<AdvancedQuery> rules = new ArrayList<>();

    for (int i = index; i < filterValues.size(); i++) {
      FilterValue filterValue = filterValues.get(i);

      switch (filterValue.getValueType()) {
        case "CLAUSE":
          rules.add(new AdvancedQuery.Rule(filterValue.getId().toString(), filterValue.getColumnUid(), filterValue.getOperator(), filterValue.getValueTxt()));
          break;
        case "OPERATOR":
          if (i == filterValues.size() - 1) {
            throw new Error("Cannot end list of rules with an operator");
          }
          if (i > 0 && filterValues.get(i - 1) != null && filterValues.get(i - 1).getValueType().equals("OPERATOR")) {
            throw new Error("Cannot follow operator with another operator");
          }

          List<AdvancedQuery> ruleGroupRules = buildRules(filterValues, index + 1);
          if (ruleGroupRules.isEmpty()) {
            throw new Error("No rules for rule group somehow");
          }

          rules.add(new AdvancedQuery.RuleGroup(filterValue.getId().toString(), filterValue.getValueType(), ruleGroupRules));
          break;
        default:
          throw new Error("Unknown value type");
      }
    }

    return rules;
  }
}

package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.models.AdvancedQuery;
import java.util.ArrayList;
import java.util.List;

public class AdvancedQueryBuilder {
  private final ReportFilter filter;

  public AdvancedQueryBuilder(ReportFilter filter) {
    this.filter = filter;
  }

  public AdvancedQuery.RuleGroup build() {
    List<FilterValue> filterValues = filter.getFilterValues();

    if (filterValues.getFirst().getValueType().equals("CLAUSE")) {
      throw new Error("First filter value must be a rule group, not a rule");
    }

    List<AdvancedQuery> rules = buildRules(filterValues, 0);
    return (AdvancedQuery.RuleGroup) rules.getFirst();
  }

  private List<AdvancedQuery> buildRules(List<FilterValue> filterValues, int start) {
    List<AdvancedQuery> rules = new ArrayList<>();

    for (int i = start; i < filterValues.size(); i++) {
      FilterValue filterValue = filterValues.get(i);

      switch (filterValue.getValueType()) {
        case "CLAUSE":
          rules.add(
              new AdvancedQuery.Rule(
                  filterValue.getId().toString(),
                  filterValue.getColumnUid(),
                  filterValue.getOperator(),
                  filterValue.getValueTxt()));
          break;
        case "OPERATOR":
          if (i == filterValues.size() - 1) {
            throw new Error("Cannot end list of rules with an operator");
          }
          if (i > 0
              && filterValues.get(i - 1) != null
              && filterValues.get(i - 1).getValueType().equals("OPERATOR")) {
            throw new Error("Cannot follow operator with another operator");
          }

          List<AdvancedQuery> ruleGroupRules = buildRules(filterValues, i + 1);
          if (ruleGroupRules.isEmpty()) {
            throw new Error("No rules for rule group somehow");
          }

          rules.add(
              new AdvancedQuery.RuleGroup(
                  filterValue.getId().toString(), filterValue.getOperator(), ruleGroupRules));
          break;
        default:
          throw new Error("Unknown value type");
      }

      // If we've reached an operator, all subsequent rules will belong to that rule group
      if (filterValue.getValueType().equals("OPERATOR")) {
        break;
      }
    }

    return rules;
  }
}

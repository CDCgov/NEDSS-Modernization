package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.models.AdvancedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdvancedQueryBuilder {
  private final ReportFilter filter;

  public AdvancedQueryBuilder(ReportFilter filter) {
    this.filter = filter;
  }

  public AdvancedQuery.RuleGroup build() {
    List<FilterValue> filterValues = filter.getFilterValues();

    if (filterValues.getFirst().getValueType().equals("OPERATOR")) {
      throw new Error("First filter value must be a CLAUSE, not an operator");
    }

//    List<AdvancedQuery> rules = buildRules(filterValues, 0);
//    return (AdvancedQuery.RuleGroup) rules.getFirst();

    List<AdvancedQuery> rules = new ArrayList<>();
    AdvancedQuery.RuleGroup root = null;

    for (int i = 0; i < filterValues.size(); i++) {
      FilterValue filterValue = filterValues.get(i);

      System.out.println(filterValue.getValueTxt());

      switch (filterValue.getValueType()) {
        case "CLAUSE":
          if (i > 0
                  && filterValues.get(i - 1) != null
                  && filterValues.get(i - 1).getValueType().equals("CLAUSE")) {
            throw new Error("CLAUSE cannot follow another CLAUSE without an OPERATOR in between");
          }
          rules.add(
                  new AdvancedQuery.Rule(
                          filterValue.getId().toString(),
                          filterValue.getColumnUid(),
                          filterValue.getOperator(),
                          filterValue.getValueTxt()));
          break;
        case "OPERATOR":
          if (i == filterValues.size() - 1) {
            throw new Error("Cannot end list of rules with an OPERATOR");
          }
          if (i > 0
                  && filterValues.get(i - 1) != null
                  && filterValues.get(i - 1).getValueType().equals("OPERATOR")
                  && !Set.of("(", ")").contains(filterValues.get(i - 1).getOperator())){
            throw new Error("Cannot follow OPERATOR with another OPERATOR, unless it's a parenthesis");
          }

          if (filterValue.getOperator().equals("and") || filterValue.getOperator().equals("or")) {
            if (root == null) {
              root = new AdvancedQuery.RuleGroup(filterValue.getId().toString(), filterValue.getOperator(), rules);
            }
          }
          break;
        default:
          throw new Error("Unknown value type");
      }
    }

    return root;
  }

  private AdvancedQuery.RuleGroup buildRuleGroup(String combinator, AdvancedQuery previousRule, List<FilterValue> filterValues, int start) {
    List<AdvancedQuery> rules = new ArrayList<>();
    rules.add(previousRule);

    AdvancedQuery.RuleGroup root = null;

    for (int i = start; i < filterValues.size(); i++) {
      FilterValue filterValue = filterValues.get(i);

      System.out.println(filterValue.getValueTxt());

      switch (filterValue.getValueType()) {
        case "CLAUSE":
          if (i > 0
                  && filterValues.get(i - 1) != null
                  && filterValues.get(i - 1).getValueType().equals("CLAUSE")) {
            throw new Error("CLAUSE cannot follow another CLAUSE without an OPERATOR in between");
          }
          rules.add(
                  new AdvancedQuery.Rule(
                          filterValue.getId().toString(),
                          filterValue.getColumnUid(),
                          filterValue.getOperator(),
                          filterValue.getValueTxt()));
          break;
        case "OPERATOR":
          if (i == filterValues.size() - 1) {
            throw new Error("Cannot end list of rules with an OPERATOR");
          }
          if (i > 0
                  && filterValues.get(i - 1) != null
                  && filterValues.get(i - 1).getValueType().equals("OPERATOR")
                  && !Set.of("(", ")").contains(filterValues.get(i - 1).getOperator())){
            throw new Error("Cannot follow OPERATOR with another OPERATOR, unless it's a parenthesis");
          }

          switch(filterValue.getOperator()) {
            case "or":
              if (combinator.equals("and")) {
                AdvancedQuery.RuleGroup andGroup = new AdvancedQuery.RuleGroup(filterValue.getId().toString(), filterValue.getOperator(), rules);
                root = buildRuleGroup("or", andGroup, filterValues, i + 1);
              }
              break;
            case "and":
              if (combinator.equals("or")) {
                AdvancedQuery firstAndRule = rules.removeLast();

                AdvancedQuery.RuleGroup andGroup = buildRuleGroup("and", firstAndRule, filterValues, i + 1);
                root = new AdvancedQuery.RuleGroup(filterValue.getId().toString(), filterValue.getOperator(), List.of(andGroup));
              }
              break;
              case "(", ")":
                break;
              default:
                throw new Error("Unknown operator");
          }
          break;
        default:
          throw new Error("Unknown value type");
      }
    }

    return root;
  }

  private List<AdvancedQuery> buildRules(List<FilterValue> filterValues, int start) {
    List<AdvancedQuery> rules = new ArrayList<>();

    for (int i = start; i < filterValues.size(); i++) {
      FilterValue filterValue = filterValues.get(i);

      switch (filterValue.getValueType()) {
        case "CLAUSE":
          if (i > 0
                  && filterValues.get(i - 1) != null
                  && filterValues.get(i - 1).getValueType().equals("CLAUSE")) {
            throw new Error("CLAUSE cannot follow another CLAUSE without an OPERATOR in between");
          }
          rules.add(
              new AdvancedQuery.Rule(
                  filterValue.getId().toString(),
                  filterValue.getColumnUid(),
                  filterValue.getOperator(),
                  filterValue.getValueTxt()));
          break;
        case "OPERATOR":
          if (i == filterValues.size() - 1) {
            throw new Error("Cannot end list of rules with an OPERATOR");
          }
          if (i > 0
              && filterValues.get(i - 1) != null
              && filterValues.get(i - 1).getValueType().equals("OPERATOR")
          && !Set.of("(", ")").contains(filterValues.get(i - 1).getOperator())){
            throw new Error("Cannot follow OPERATOR with another OPERATOR, unless it's a parenthesis");
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

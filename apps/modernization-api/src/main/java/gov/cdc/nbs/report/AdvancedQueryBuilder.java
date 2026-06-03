package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.models.AdvancedQuery;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdvancedQueryBuilder {
  private final ReportFilter filter;
  private final List<Error> queryErrors = new ArrayList<>();

  static class QueryError extends Error {
    @Getter
    private FilterValue filterValue;

    public QueryError(String message) {
      super(message);
    }

    public QueryError(String message, FilterValue filterValue) {
      super(message);
      this.filterValue = filterValue;
    }
  }

  public AdvancedQueryBuilder(ReportFilter filter) {
    this.filter = filter;
  }

  private int current = 0;
  private boolean hasNext() {
    return current < filter.getFilterValues().size() - 1;
  }

  private FilterValue peek() {
    return filter.getFilterValues().get(current);
  }

  private FilterValue previous() {
    if (current == 0) return null;
    return filter.getFilterValues().get(current - 1);
  }

  private void advance() {
    if (hasNext()) current++;
  }

  public AdvancedQuery.RuleGroup build() {
    if (peek().getValueType().equals("OPERATOR")) {
      queryErrors.add(new QueryError("First filter value must be a CLAUSE, not an operator", peek()));
    }

    return buildRuleGroup(ReportConstants.QueryCombinators.and, null, null);
  }

  /**
   *
   * @param combinator
   * @param previousRule
   * @param ruleGroupId ID to use when creating a new RuleGroup (will always be the corresponding FilterValue ID)
   * @return
   */
  private AdvancedQuery.RuleGroup buildRuleGroup(ReportConstants.QueryCombinators combinator, AdvancedQuery previousRule, String ruleGroupId) {
    List<AdvancedQuery> rules = new ArrayList<>();

    if (previousRule != null) {
      rules.add(previousRule);
    }

    AdvancedQuery.RuleGroup root = null;
    int nestDepth = 0;
    boolean terminated = false;

    while (hasNext()) {
      FilterValue filterValue = peek();

      switch (filterValue.getValueType()) {
        case "CLAUSE":
          if (previous() != null && previous().getValueType().equals("CLAUSE")) {
            queryErrors.add(new QueryError("CLAUSE cannot follow another CLAUSE without an OPERATOR in between", filterValue));
          }
          rules.add(
                  new AdvancedQuery.Rule(
                          filterValue.getId().toString(),
                          filterValue.getColumnUid(),
                          filterValue.getOperator(),
                          filterValue.getValueTxt()));
          break;
        case "OPERATOR":
          if (current == filter.getFilterValues().size() - 1) {
            queryErrors.add(new QueryError("Cannot end list of rules with an OPERATOR", filterValue));
          }
          if (previous() != null && previous().getValueType().equals("OPERATOR")
                  && !Set.of("(", ")").contains(previous().getOperator())){
            queryErrors.add(new QueryError("Cannot follow OPERATOR with another OPERATOR, unless it's a parenthesis", filterValue));
          }

          switch(filterValue.getOperator()) {
            case "or":
              if (combinator.equals(ReportConstants.QueryCombinators.and)) {
                //  If going directly from an 'AND' group to an 'OR' group, WITHOUT parens
                if (nestDepth == 0) {
                  //  We terminate the current 'AND' group
                  AdvancedQuery.RuleGroup andGroup = new AdvancedQuery.RuleGroup(ruleGroupId, ReportConstants.QueryCombinators.and, rules);
                  terminated = true;

                  // And add it as a rule to the new 'OR' group, which we then build
                  root = buildRuleGroup(ReportConstants.QueryCombinators.or, andGroup, filterValue.getId().toString());

                  //  If going from an 'AND' group to an 'OR' group WITHIN parens
                } else {
                  //  We just build the 'OR' group, starting from the previous FilterValue
                  AdvancedQuery firstOrRule = rules.removeLast();
                  AdvancedQuery.RuleGroup orGroup = buildRuleGroup(ReportConstants.QueryCombinators.or, firstOrRule, filterValue.getId().toString());

                  // And add it to the existing list of 'AND' group rules
                  rules.add(orGroup);
                }
              }
              break;
            case "and":
              //  If going directly from an 'OR' group to an 'AND' group, WITH OR WITHOUT parens
              if (combinator.equals(ReportConstants.QueryCombinators.or)) {
                //  We build out the 'AND' group to completion, starting from the previous FilterValue
                AdvancedQuery firstAndRule = rules.removeLast();
                AdvancedQuery.RuleGroup andGroup = buildRuleGroup(ReportConstants.QueryCombinators.and, firstAndRule, filterValue.getId().toString());

                // And attach it to the existing list of 'OR' group rules
                rules.add(andGroup);
              }
              break;
            case "(":
              if (previous() != null && previous().getOperator().equals(")")) {
                queryErrors.add(new QueryError("Cannot follow a closing parenthesis with an open parenthesis without an OPERATOR in between", filterValue));
              }
              nestDepth++;
              break;
            case ")":
              // If we encounter a closed parenthesis without any nesting, terminate the rule group
              if (nestDepth == 0) {
                terminated = true;
              } else {
                nestDepth--;
              }

              if (nestDepth < 0) {
                queryErrors.add(new QueryError("Too many closing parentheses", filterValue));
              }
              break;
              default:
                queryErrors.add(new QueryError("Unknown operator: " + filterValue.getOperator(), filterValue));
          }
          break;
        default:
          queryErrors.add(new QueryError("Unknown value type encountered: " + filterValue.getValueType(), filterValue));
      }
      advance();

      if (terminated) {
        break;
      }
    }

    if (nestDepth != 0) {
      queryErrors.add(new QueryError("Mismatched parentheses"));
    }

    if (root == null) {
      root = new AdvancedQuery.RuleGroup(ruleGroupId, combinator, rules);
    }

    return root;
  }
}

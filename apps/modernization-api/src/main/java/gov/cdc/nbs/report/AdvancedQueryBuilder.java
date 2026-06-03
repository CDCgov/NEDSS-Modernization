package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.report.models.AdvancedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdvancedQueryBuilder {
  private final List<FilterValue> filterValues;
  private final List<AdvancedQueryException> queryErrors = new ArrayList<>();

  /** 0-based index of the current {@code FilterValue} being processed */
  private int current = 0;

  /** Specifies if there is another {@code FilterValue} to be processed */
  private boolean hasNext() {
    return current < filterValues.size() - 1;
  }

  /** Returns the current {@code FilterValue} */
  private FilterValue peek() {
    return filterValues.get(current);
  }

  /** Returns the last processed {@code FilterValue} */
  private FilterValue previous() {
    if (current == 0) return null;
    return filterValues.get(current - 1);
  }

  /** Advances the current index to the next {@code FilterValue} in the list */
  private void advance() {
    if (hasNext()) current++;
  }

  public AdvancedQueryBuilder(List<FilterValue> filterValues) {
    this.filterValues = filterValues;
  }

  public AdvancedQuery.RuleGroup build() {
    return buildRuleGroup(ReportConstants.QueryCombinators.and, null);
  }

  private AdvancedQuery.RuleGroup buildRuleGroup(
      ReportConstants.QueryCombinators combinator, AdvancedQuery previousRule) {
    List<AdvancedQuery> rules = new ArrayList<>();
    if (previousRule != null) {
      rules.add(previousRule);
    }

    AdvancedQuery.RuleGroup ruleGroup = null;
    int nestDepth = 0;
    boolean terminated = false;

    while (hasNext()) {
      FilterValue filterValue = peek();

      switch (filterValue.getValueType()) {
        case "CLAUSE":
          validateClause(filterValue);

          rules.add(
              new AdvancedQuery.Rule(
                  filterValue.getId().toString(),
                  filterValue.getColumnUid(),
                  filterValue.getOperator(),
                  filterValue.getValueTxt()));
          break;
        case "OPERATOR":
          validateOperator(filterValue);

          switch (filterValue.getOperator()) {
            case "or":
              if (combinator.equals(ReportConstants.QueryCombinators.and)) {
                //  If going directly from an 'AND' group to an 'OR' group, WITHOUT parens
                if (nestDepth == 0) {
                  //  We terminate the current 'AND' group
                  AdvancedQuery.RuleGroup andGroup =
                      new AdvancedQuery.RuleGroup(
                          UUID.randomUUID().toString(),
                          ReportConstants.QueryCombinators.and,
                          rules);
                  terminated = true;

                  // And add it as a rule to the new 'OR' group, which we then build
                  ruleGroup = buildRuleGroup(ReportConstants.QueryCombinators.or, andGroup);

                  //  If going from an 'AND' group to an 'OR' group WITHIN parens
                } else {
                  //  We just build the 'OR' group, starting from the previous FilterValue
                  AdvancedQuery firstOrRule = rules.removeLast();
                  AdvancedQuery.RuleGroup orGroup =
                      buildRuleGroup(ReportConstants.QueryCombinators.or, firstOrRule);

                  // And add it to the existing list of 'AND' group rules
                  rules.add(orGroup);
                }
              }
              break;
            case "and":
              //  If going directly from an 'OR' group to an 'AND' group, WITH OR WITHOUT parens
              if (combinator.equals(ReportConstants.QueryCombinators.or)) {
                //  We build out the 'AND' group to completion, starting from the previous
                // FilterValue
                AdvancedQuery firstAndRule = rules.removeLast();
                AdvancedQuery.RuleGroup andGroup =
                    buildRuleGroup(ReportConstants.QueryCombinators.and, firstAndRule);

                // And attach it to the existing list of 'OR' group rules
                rules.add(andGroup);
              }
              break;
            case "(":
              if (previous() != null && previous().getOperator().equals(")")) {
                queryErrors.add(
                    new AdvancedQueryException(
                        "Cannot follow a closing parenthesis with an open parenthesis without an OPERATOR in between",
                        filterValue));
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
                queryErrors.add(
                    new AdvancedQueryException("Too many closing parentheses", filterValue));
              }
              break;
            default:
              queryErrors.add(
                  new AdvancedQueryException(
                      "Unknown operator: " + filterValue.getOperator(), filterValue));
          }
          break;
        default:
          queryErrors.add(
              new AdvancedQueryException(
                  "Unknown valueType encountered: " + filterValue.getValueType(), filterValue));
      }
      advance();

      if (terminated) {
        break;
      }
    }

    if (nestDepth != 0) {
      queryErrors.add(new AdvancedQueryException("Mismatched parentheses"));
    }

    if (!queryErrors.isEmpty()) {
      //  TODO: What exactly do we wanna do here?  Should we still return the partially built
      // ruleGroup
      //  alongside the list of errors?  Given we presumably want to surface these errors to the
      // user in
      //  a non-blocking way, should this still be considered a 2XX response?
    }

    if (ruleGroup == null) {
      ruleGroup = new AdvancedQuery.RuleGroup(UUID.randomUUID().toString(), combinator, rules);
    }

    return ruleGroup;
  }

  private void validateClause(FilterValue filterValue) {
    if (previous() != null && previous().getValueType().equals("CLAUSE")) {
      queryErrors.add(
          new AdvancedQueryException(
              "CLAUSE cannot follow another CLAUSE without an OPERATOR in between", filterValue));
    }
  }

  private void validateOperator(FilterValue filterValue) {
    if (previous() == null) {
      queryErrors.add(
          new AdvancedQueryException(
              "First filter value must be a CLAUSE, not an OPERATOR", peek()));
    }
    if (current == filterValues.size() - 1 && !filterValue.getOperator().equals(")")) {
      queryErrors.add(
          new AdvancedQueryException(
              "Cannot end list of FilterValues with an OPERATOR unless it's a closing parenthesis",
              filterValue));
    }
    if (previous() != null
        && previous().getValueType().equals("OPERATOR")
        && !filterValue.getOperator().equals(")")
        && !previous().getOperator().equals("(")) {
      queryErrors.add(
          new AdvancedQueryException(
              "Cannot follow OPERATOR with another OPERATOR, unless it's a parenthesis",
              filterValue));
    }
  }
}

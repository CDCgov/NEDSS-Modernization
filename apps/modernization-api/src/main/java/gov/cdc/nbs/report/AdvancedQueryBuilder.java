package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.report.models.AdvancedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdvancedQueryBuilder {
  private final List<FilterValue> filterValues;

  /** 0-based index of the current {@code FilterValue} being processed */
  private int current = 0;

  private int parenDepth = 0;

  /** Specifies if there is another {@code FilterValue} to be processed */
  private boolean hasNext() {
    return current < filterValues.size();
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

  public AdvancedQuery.RuleGroup build() throws AdvancedQueryException {
    RuleGroupParams firstRuleGroupParams = fetchRootRuleGroupParams();

    // Build the root RuleGroup from the first OPERATOR and corresponding rule
    AdvancedQuery.RuleGroup ruleGroup =
        buildRuleGroup(firstRuleGroupParams.combinator, firstRuleGroupParams.rule);

    if (hasNext()) {
      throw new AdvancedQueryException("Unexpected trailing FilterValues", filterValues);
    }

    if (parenDepth != 0) {
      throw new AdvancedQueryException("Unbalanced parentheses: " + parenDepth, null);
    }

    System.out.println(ruleGroup);
    return ruleGroup;
  }

  private AdvancedQuery.RuleGroup buildRuleGroup(
      ReportConstants.QueryCombinators combinator, AdvancedQuery previousRule)
      throws AdvancedQueryException {
    List<AdvancedQuery> rules = new ArrayList<>();
    if (previousRule != null) {
      rules.add(previousRule);
    }

    AdvancedQuery.RuleGroup ruleGroup = null;
    int nestDepth = 0;
    boolean terminated = false;

    while (hasNext()) {
      FilterValue filterValue = peek();

      if (isClause(filterValue)) {
        rules.add(buildClause(filterValue));
      } else if (isOperator(filterValue)) {
        validateOperator(filterValue);

        if (isOr(filterValue)) {
          if (combinator.equals(ReportConstants.QueryCombinators.and)) {
            //  If going directly from an 'AND' group to an 'OR' group, without parens
            if (nestDepth == 0) {
              //  We terminate the current 'AND' group
              terminated = true;
              AdvancedQuery.RuleGroup andGroup =
                  new AdvancedQuery.RuleGroup(
                      UUID.randomUUID().toString(), ReportConstants.QueryCombinators.and, rules);

              // And add it as a rule to the new 'OR' group, which we then build
              ruleGroup = buildRuleGroup(ReportConstants.QueryCombinators.or, andGroup);

              //  If going from an 'AND' group to an 'OR' group WITHIN inner parens
            } else {
              //  We just build the 'OR' group, starting from the previous FilterValue
              AdvancedQuery firstOrRule = rules.removeLast();
              AdvancedQuery.RuleGroup orGroup =
                  buildRuleGroup(ReportConstants.QueryCombinators.or, firstOrRule);

              // And add it to the existing list of 'AND' group rules
              rules.add(orGroup);
            }
          }
        } else if (isAnd(filterValue)) {
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
        } else if (isOpenParen(filterValue)) {
          nestDepth++;
          parenDepth++;
        } else if (isCloseParen(filterValue)) {
          // If we encounter a closed parenthesis without any nesting, terminate the rule group
          if (nestDepth == 0) {
            terminated = true;
          } else {
            nestDepth--;
          }

          parenDepth--;
        } else {
          throw new AdvancedQueryException(
              "Unknown operator: " + filterValue.getOperator(), filterValues);
        }
      } else {
        throw new AdvancedQueryException(
            "Unknown valueType encountered: " + filterValue.getValueType(), filterValues);
      }
      advance();

      if (terminated) {
        break;
      }
    }

    if (ruleGroup == null) {
      ruleGroup = new AdvancedQuery.RuleGroup(UUID.randomUUID().toString(), combinator, rules);
    }

    return ruleGroup;
  }

  private boolean isClause(FilterValue filterValue) {
    return filterValue.getValueType().equals("CLAUSE");
  }

  private boolean isOperator(FilterValue filterValue) {
    return filterValue.getValueType().equals("OPERATOR");
  }

  private boolean isOr(FilterValue filterValue) {
    return isOperator(filterValue) && filterValue.getOperator().equals("or");
  }

  private boolean isAnd(FilterValue filterValue) {
    return isOperator(filterValue) && filterValue.getOperator().equals("and");
  }

  private boolean isParen(FilterValue filterValue) {
    return isOpenParen(filterValue) || isCloseParen(filterValue);
  }

  private boolean isOpenParen(FilterValue filterValue) {
    return isOperator(filterValue) && filterValue.getOperator().equals("(");
  }

  private boolean isCloseParen(FilterValue filterValue) {
    return isOperator(filterValue) && filterValue.getOperator().equals(")");
  }

  private AdvancedQuery.Rule buildClause(FilterValue filterValue) throws AdvancedQueryException {
    validateClause(filterValue);

    return new AdvancedQuery.Rule(
        filterValue.getId().toString(),
        filterValue.getColumnUid(),
        filterValue.getOperator(),
        filterValue.getValueTxt());
  }

  private void validateClause(FilterValue filterValue) throws AdvancedQueryException {
    if (previous() != null && previous().getValueType().equals("CLAUSE")) {
      throw new AdvancedQueryException(
          "CLAUSE cannot follow another CLAUSE without an OPERATOR in between", filterValues);
    }
  }

  private void validateOperator(FilterValue filterValue) throws AdvancedQueryException {
    if (previous() == null && !isOpenParen(filterValue)) {
      throw new AdvancedQueryException(
          "First filter value must be a CLAUSE, not an OPERATOR", filterValues);
    }
    if (current == filterValues.size() - 1 && !isCloseParen(filterValue)) {
      throw new AdvancedQueryException(
          "Cannot end list of FilterValues with an OPERATOR unless it's a closing parenthesis",
          filterValues);
    }
    if (previous() != null && isOperator(previous())) {
      if (isCloseParen(filterValue) && isOpenParen(previous())) {
        throw new AdvancedQueryException("Empty parentheses are not allowed", filterValues);
      }
      if (!isParen(filterValue) && !isParen(previous())) {
        throw new AdvancedQueryException(
            "'and/or' OPERATOR cannot follow another 'and/or' OPERATOR", filterValues);
      }
      if (isCloseParen(previous()) && isOpenParen(filterValue)) {
        throw new AdvancedQueryException(
            "Cannot follow a closing parenthesis with an open parenthesis", filterValues);
      }
    }
  }

  record RuleGroupParams(ReportConstants.QueryCombinators combinator, AdvancedQuery.Rule rule) {}

  private RuleGroupParams fetchRootRuleGroupParams() throws AdvancedQueryException {
    AdvancedQuery.Rule firstRule = null;
    ReportConstants.QueryCombinators firstCombinator = null;

    // Iterate through FilterValues until we find the first OPERATOR
    while (hasNext()) {
      FilterValue filterValue = peek();
      if (isClause(filterValue)) {
        firstRule = buildClause(filterValue);
      } else if (isOperator(filterValue)) {
        validateOperator(filterValue);

        if (isAnd(filterValue) || isOr(filterValue)) {
          firstCombinator = ReportConstants.QueryCombinators.valueOf(filterValue.getOperator());
          break;
        } else if (isOpenParen(filterValue)) {
          parenDepth++;
        } else if (isCloseParen(filterValue)) {
          parenDepth--;
        } else {
          throw new AdvancedQueryException(
              "Unknown operator: " + filterValue.getOperator(), filterValues);
        }
      } else {
        throw new AdvancedQueryException(
            "Unknown valueType encountered: " + filterValue.getValueType(), filterValues);
      }
      advance();
    }

    // If we didn't find an OPERATOR, default to 'AND' combinator for a single rule
    if (firstCombinator == null) {
      firstCombinator = ReportConstants.QueryCombinators.and;
    }

    return new RuleGroupParams(firstCombinator, firstRule);
  }
}

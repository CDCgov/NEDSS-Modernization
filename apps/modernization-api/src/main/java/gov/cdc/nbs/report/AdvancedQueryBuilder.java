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
    AdvancedQuery.RuleGroup ruleGroup = buildRuleGroup(ReportConstants.QueryCombinators.and, null);

    if (parenDepth != 0) {
      throw new AdvancedQueryException("Mismatched parentheses");
    }

    return unwrapRuleGroup(ruleGroup);
  }

  private AdvancedQuery.RuleGroup unwrapRuleGroup(AdvancedQuery.RuleGroup ruleGroup) {
    //  If a RuleGroup has only one Rule, and it's also a RuleGroup
    if (ruleGroup.rules().size() == 1
        && ruleGroup.rules().getFirst() instanceof AdvancedQuery.RuleGroup) {

      //  We can do away with the outer RuleGroup
      return unwrapRuleGroup((AdvancedQuery.RuleGroup) ruleGroup.rules().getFirst());
    } else if (ruleGroup.rules().size() > 1) {
      return new AdvancedQuery.RuleGroup(
          ruleGroup.id(),
          ruleGroup.combinator(),
          ruleGroup.rules().stream()
              .map(
                  rule -> {
                    //  If a RuleGroup's rule is a RuleGroup, and it has only one Rule
                    if (rule instanceof AdvancedQuery.RuleGroup nestedRuleGroup) {
                      if ((nestedRuleGroup.rules().size() == 1
                          && nestedRuleGroup.rules().getFirst() instanceof AdvancedQuery.Rule)) {
                        //  Bypass the nested RuleGroup and attach the inner Rule to the outer
                        // RuleGroup
                        return nestedRuleGroup.rules().getFirst();
                      } else {
                        return unwrapRuleGroup(nestedRuleGroup);
                      }
                    } else {
                      return rule;
                    }
                  })
              .toList());
    }
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
    boolean terminated = false;

    while (hasNext()) {
      FilterValue filterValue = peek();

      if (isClause(filterValue)) {
        rules.add(buildClause(filterValue));
      } else if (isOperator(filterValue)) {
        validateOperator(filterValue);

        if (isOr(filterValue)) {
          if (combinator.equals(ReportConstants.QueryCombinators.and)) {
            //  If going directly from an 'AND' group to an 'OR' group, terminate the current
            // 'AND' group
            terminated = true;
            AdvancedQuery.RuleGroup andGroup =
                new AdvancedQuery.RuleGroup(
                    UUID.randomUUID().toString(), ReportConstants.QueryCombinators.and, rules);

            // And add it as a rule to the new 'OR' group, which we then build
            ruleGroup = buildRuleGroup(ReportConstants.QueryCombinators.or, andGroup);
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
          if (previous() != null && isCloseParen(previous())) {
            throw new AdvancedQueryException(
                "Cannot follow a closing parenthesis with an open parenthesis without an OPERATOR in between",
                filterValue);
          }
          parenDepth++;
          advance();
          rules.add(buildRuleGroup(ReportConstants.QueryCombinators.and, null));
        } else if (isCloseParen(filterValue)) {
          // If we encounter a closed parenthesis without any nesting, terminate the rule group
          terminated = true;
          parenDepth--;
        } else {
          throw new AdvancedQueryException(
              "Unknown operator: " + filterValue.getOperator(), filterValue);
        }
      } else {
        throw new AdvancedQueryException(
            "Unknown valueType encountered: " + filterValue.getValueType(), filterValue);
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

  private boolean isOperator(FilterValue filterValue) {
    return filterValue.getValueType().equals("OPERATOR");
  }

  private boolean isClause(FilterValue filterValue) {
    return filterValue.getValueType().equals("CLAUSE");
  }

  private boolean isAnd(FilterValue filterValue) {
    return filterValue.getOperator().equals("and");
  }

  private boolean isOr(FilterValue filterValue) {
    return filterValue.getOperator().equals("or");
  }

  private boolean isOpenParen(FilterValue filterValue) {
    return filterValue.getOperator().equals("(");
  }

  private boolean isCloseParen(FilterValue filterValue) {
    return filterValue.getOperator().equals(")");
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
    if (previous() != null && isClause(previous())) {
      throw new AdvancedQueryException(
          "CLAUSE cannot follow another CLAUSE without an OPERATOR in between", filterValue);
    }
  }

  private void validateOperator(FilterValue filterValue) throws AdvancedQueryException {
    if (current == filterValues.size() - 1 && !isCloseParen(filterValue)) {
      throw new AdvancedQueryException(
          "Cannot end list of FilterValues with an OPERATOR unless it's a closing parenthesis",
          filterValue);
    }
  }
}

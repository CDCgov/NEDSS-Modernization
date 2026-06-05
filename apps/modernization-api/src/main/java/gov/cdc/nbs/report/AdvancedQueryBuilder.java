package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.report.models.AdvancedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdvancedQueryBuilder {
  private final ArrayList<FilterValue> filterValues;

  private final FilterValue OPEN_PAREN = new FilterValue(1L, null, 1, "OPERATOR", null, "(", null);
  private final FilterValue CLOSE_PAREN = new FilterValue(2L, null, 1, "OPERATOR", null, ")", null);

  /** 0-based index of the current {@code FilterValue} being processed */
  private int current = 0;

  /** Specifies if there is another {@code FilterValue} to be processed */
  private boolean hasNext() {
    return current < filterValues.size();
  }

  /** Returns the current {@code FilterValue} */
  private FilterValue peek() {
    return filterValues.get(current);
  }

  /** Advances the current index to the next {@code FilterValue} in the list */
  private void advance() {
    if (hasNext()) current++;
  }

  public AdvancedQueryBuilder(List<FilterValue> filterValues) {
    this.filterValues = new ArrayList<>(filterValues);
  }

  public AdvancedQuery.RuleGroup build() throws AdvancedQueryException {
    // wrap whole thing in () to make sure it's a valid rule group
    this.filterValues.add(0, OPEN_PAREN);
    this.filterValues.add(CLOSE_PAREN);

    AdvancedQuery.RuleGroup res = startRuleGroup();

    if (current != filterValues.size()) {
      throw new Error("Extra query left over :(");
    }

    return simplify(res);
  }

  private AdvancedQuery.RuleGroup startRuleGroup() throws AdvancedQueryException {
    FilterValue openParen = peek();
    if (!isOpenParen(openParen))
      throw new AdvancedQueryException("Expected paren to open rule group", openParen);
    advance();

    AdvancedQuery firstRule = null;
    FilterValue next = peek();
    if (isOpenParen(next)) {
      firstRule = startRuleGroup();
    } else if (isClause(next)) {
      firstRule = buildClause(next);
      advance();
    } else if (isCloseParen(next)) { // "(" ")"
      throw new AdvancedQueryException("Invalid empty clause `()`", next);
    } else {
      throw new AdvancedQueryException("Expected new rule or rule group", next);
    }

    FilterValue combinator = peek();
    // "(" CLAUSE ")"
    if (isCloseParen(combinator)) {
      advance();
      return new AdvancedQuery.RuleGroup(
          combinator.getId().toString(), ReportConstants.QueryCombinators.and, List.of(firstRule));
    } else if (!isCombinator(combinator))
      throw new AdvancedQueryException("Expected 'and' or 'or'", combinator);
    ReportConstants.QueryCombinators firstCombinator =
        ReportConstants.QueryCombinators.valueOf(combinator.getOperator());
    advance();

    // Then build the root RuleGroup from said OPERATOR and corresponding rule
    AdvancedQuery.RuleGroup ruleGroup = buildRuleGroup(firstCombinator, firstRule);

    if (!hasNext()) throw new AdvancedQueryException("Expected closing paren");

    FilterValue closingParen = peek();
    if (!isCloseParen(closingParen))
      throw new AdvancedQueryException("Expected closing paren", closingParen);
    advance();

    System.out.println(ruleGroup);
    return ruleGroup;
  }

  /// ( col = 1 AND -> next token should be "(" OR CLAUSE
  private AdvancedQuery.RuleGroup buildRuleGroup(
      ReportConstants.QueryCombinators combinator, AdvancedQuery previousRule)
      throws AdvancedQueryException {
    List<AdvancedQuery> rules = new ArrayList<>();
    rules.add(previousRule);

    AdvancedQuery.RuleGroup ruleGroup = null;

    while (hasNext()) {
      FilterValue filterValue = peek();

      if (isClause(filterValue)) {
        rules.add(buildClause(filterValue));
        advance();
        FilterValue next = peek();

        if (!isOperator(next))
          throw new AdvancedQueryException("operator must follow clause", next);

        if (isCombinator(next)) {

          if (combinator.equals(ReportConstants.QueryCombinators.valueOf(next.getOperator()))) {
            advance(); // keep on keeping on
            continue;
          }

          // and => or
          if (isOr(next) && combinator.equals(ReportConstants.QueryCombinators.and)) {
            //  We terminate the current 'AND' group
            AdvancedQuery.RuleGroup andGroup =
                new AdvancedQuery.RuleGroup(
                    UUID.randomUUID().toString(), ReportConstants.QueryCombinators.and, rules);
            advance();

            // And add it as a rule to the new 'OR' group, which we then build
            ruleGroup = buildRuleGroup(ReportConstants.QueryCombinators.or, andGroup);

            // or => and
          } else if (isAnd(next) && combinator.equals(ReportConstants.QueryCombinators.or)) {
            //  We build out the 'AND' group to completion, starting from the previous
            // FilterValue
            AdvancedQuery rule = rules.removeLast();
            advance();
            AdvancedQuery.RuleGroup andGroup =
                buildRuleGroup(ReportConstants.QueryCombinators.and, rule);

            // And attach it to the existing list of 'OR' group rules
            rules.add(andGroup);
          }
        }

      } else if (isOpenParen(filterValue)) {
        rules.add(startRuleGroup());
      } else {
        throw new AdvancedQueryException("') invalid after operator", filterValue);
      }

      if (isCloseParen(peek())) {
        if (ruleGroup == null) {
          ruleGroup = new AdvancedQuery.RuleGroup(UUID.randomUUID().toString(), combinator, rules);
        }
        return ruleGroup;
      }

      advance();
    }

    if (ruleGroup == null) {
      ruleGroup = new AdvancedQuery.RuleGroup(UUID.randomUUID().toString(), combinator, rules);
    }

    return ruleGroup;
  }

  private AdvancedQuery.Rule buildClause(FilterValue filterValue) {
    return new AdvancedQuery.Rule(
        filterValue.getId().toString(),
        filterValue.getColumnUid(),
        filterValue.getOperator(),
        filterValue.getValueTxt());
  }

  private AdvancedQuery.RuleGroup simplify(AdvancedQuery.RuleGroup ruleGroup) {
    //  If a RuleGroup has only one Rule, and it's also a RuleGroup
    if (ruleGroup.rules().size() == 1
        && ruleGroup.rules().getFirst() instanceof AdvancedQuery.RuleGroup) {

      //  We can do away with the outer RuleGroup
      return simplify((AdvancedQuery.RuleGroup) ruleGroup.rules().getFirst());
    } else {
      return new AdvancedQuery.RuleGroup(
          ruleGroup.id(),
          ruleGroup.combinator(),
          ruleGroup.rules().stream()
              .map(
                  (AdvancedQuery rule) ->
                      (rule instanceof AdvancedQuery.RuleGroup)
                          ? simplify((AdvancedQuery.RuleGroup) rule)
                          : rule)
              .toList());
    }
  }

  private boolean isOpenParen(FilterValue fv) {
    return isOperator(fv) && fv.getOperator().equals("(");
  }

  private boolean isCloseParen(FilterValue fv) {
    return isOperator(fv) && fv.getOperator().equals(")");
  }

  private boolean isOr(FilterValue fv) {
    return isOperator(fv) && fv.getOperator().equals("or");
  }

  private boolean isAnd(FilterValue fv) {
    return isOperator(fv) && fv.getOperator().equals("and");
  }

  private boolean isClause(FilterValue fv) {
    return fv.getValueType().equals("CLAUSE");
  }

  private boolean isOperator(FilterValue fv) {
    return fv.getValueType().equals("OPERATOR");
  }

  private boolean isCombinator(FilterValue fv) {
    return isAnd(fv) || isOr(fv);
  }
}

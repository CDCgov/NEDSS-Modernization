package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.report.models.AdvancedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdvancedQueryBuilder {
  private final ArrayList<FilterValue> filterValues;
  private final List<AdvancedQueryException> queryErrors = new ArrayList<>();

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
    this.filterValues = new ArrayList<>(filterValues);
  }

  public AdvancedQuery.RuleGroup build() throws AdvancedQueryException {
    // wrap whole thing in () to make sure it's a valid rule group
    this.filterValues.add(0, OPEN_PAREN);
    this.filterValues.add(CLOSE_PAREN);

    AdvancedQuery.RuleGroup res = startRuleGroup();
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
    } else if (isCloseParen(next)) { // "(" ")"
      return new AdvancedQuery.RuleGroup(
          next.getId().toString(), ReportConstants.QueryCombinators.and, List.of());
    } else {
      throw new AdvancedQueryException("Expected new rule or rule group", next);
    }
    advance();

    FilterValue combinator = peek();
    // "(" CLAUSE ")"
    if (isCloseParen(combinator)) {
      return new AdvancedQuery.RuleGroup(
          combinator.getId().toString(), ReportConstants.QueryCombinators.and, List.of(firstRule));
    } else if (!isCombinator(combinator))
      throw new AdvancedQueryException("Expected 'and' or 'or'", combinator);
    ReportConstants.QueryCombinators firstCombinator =
        ReportConstants.QueryCombinators.valueOf(combinator.getOperator());

    // Then build the root RuleGroup from said OPERATOR and corresponding rule
    AdvancedQuery.RuleGroup ruleGroup = buildRuleGroup(firstCombinator, firstRule);

    System.out.println(ruleGroup);
    return ruleGroup;
  }

  /// ( col = 1 AND -> next token should be "(" OR CLAUSE
  private AdvancedQuery.RuleGroup buildRuleGroup(
      ReportConstants.QueryCombinators combinator, AdvancedQuery previousRule)
      throws AdvancedQueryException {
    List<AdvancedQuery> rules = new ArrayList<>();
    if (previousRule != null) {
      rules.add(previousRule);
    }

    AdvancedQuery.RuleGroup ruleGroup = null;
    int nestDepth = 1; // already ate opening paren
    boolean terminated = false;

    while (hasNext()) {
      FilterValue filterValue = peek();

      switch (filterValue.getValueType()) {
        case "CLAUSE":
          rules.add(buildClause(filterValue));
          advance();
          FilterValue next = peek();

          if (next.getValueType() != "OPERATOR")
            throw new AdvancedQueryException("operator must follow clause", next);

          switch (next.getOperator()) {
            case "or":
              if (combinator.equals(ReportConstants.QueryCombinators.and)) {
                //  If going directly from an 'AND' group to an 'OR' group, without parens
                if (nestDepth == 0) {
                  //  We terminate the current 'AND' group
                  terminated = true;
                  AdvancedQuery.RuleGroup andGroup =
                      new AdvancedQuery.RuleGroup(
                          UUID.randomUUID().toString(),
                          ReportConstants.QueryCombinators.and,
                          rules);

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
            case ")":
              nestDepth--;

              // If we encounter a closed parenthesis without any nesting, terminate the rule group
              if (nestDepth == 0) {
                terminated = true;
              } else if (nestDepth < 0)
                throw new AdvancedQueryException("Too many closing parentheses", filterValue);
              break;
            default:
              throw new AdvancedQueryException("Unexpected operator after clause");
          }
          break;
        case "OPERATOR":
          if (filterValue.getOperator() != "(")
            throw new AdvancedQueryException("non-open paren after operator", filterValue);

          rules.add(startRuleGroup());
          break;
        default:
          throw new AdvancedQueryException(
              "Unknown valueType encountered: " + filterValue.getValueType(), filterValue);
      }
      advance();

      if (terminated) {
        break;
      }
    }

    if (nestDepth != 0) {
      throw new AdvancedQueryException("Mismatched parentheses");
    }

    if (ruleGroup == null) {
      ruleGroup = new AdvancedQuery.RuleGroup(UUID.randomUUID().toString(), combinator, rules);
    }

    return ruleGroup;
  }

  private AdvancedQuery.Rule buildClause(FilterValue filterValue) {
    // shouldn't be needed anymore maybe?
    // validateClause(filterValue);

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
              .filter(
                  (AdvancedQuery rule) ->
                      (rule instanceof AdvancedQuery.Rule)
                          || ((AdvancedQuery.RuleGroup) rule).rules().size() > 0)
              .map(
                  (AdvancedQuery rule) ->
                      (rule instanceof AdvancedQuery.RuleGroup nestedRuleGroup)
                          ? simplify((AdvancedQuery.RuleGroup) rule)
                          : rule)
              .toList());
    }
  }

  private boolean isOpenParen(FilterValue fv) {
    return fv.getValueType() != "OPERATOR" && fv.getOperator().equals("(");
  }

  private boolean isCloseParen(FilterValue fv) {
    return fv.getValueType() != "OPERATOR" && fv.getOperator().equals(")");
  }

  private boolean isClause(FilterValue fv) {
    return fv.getValueType().equals("CLAUSE");
  }

  private boolean isCombinator(FilterValue fv) {
    return fv.getValueType().equals("OPERATOR")
        && (fv.getOperator().equals("or") || fv.getOperator().equals("and"));
  }

  // DEAD I THINK?
  //   private void validateClause(FilterValue filterValue) {
  //     if (previous() != null && previous().getValueType().equals("CLAUSE")) {
  //       queryErrors.add(
  //           new AdvancedQueryException(
  //               "CLAUSE cannot follow another CLAUSE without an OPERATOR in between",
  // filterValue));
  //     }
  //   }

  //   private void validateOperator(FilterValue filterValue) {
  //     if (previous() == null) {
  //       queryErrors.add(
  //           new AdvancedQueryException(
  //               "First filter value must be a CLAUSE, not an OPERATOR", peek()));
  //     }
  //     if (current == filterValues.size() - 1 && !filterValue.getOperator().equals(")")) {
  //       queryErrors.add(
  //           new AdvancedQueryException(
  //               "Cannot end list of FilterValues with an OPERATOR unless it's a closing
  // parenthesis",
  //               filterValue));
  //     }
  //     if (previous() != null
  //         && previous().getValueType().equals("OPERATOR")
  //         && !filterValue.getOperator().equals(")")
  //         && !previous().getOperator().equals("(")) {
  //       queryErrors.add(
  //           new AdvancedQueryException(
  //               "Cannot follow OPERATOR with another OPERATOR, unless it's a parenthesis",
  //               filterValue));
  //     }
  //   }
}

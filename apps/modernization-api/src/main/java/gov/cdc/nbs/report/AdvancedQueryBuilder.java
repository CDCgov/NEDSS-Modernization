package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.report.models.AdvancedQuery;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class AdvancedQueryBuilder {
  private static final System.Logger LOGGER =
      System.getLogger(AdvancedQueryBuilder.class.getName());

  private final ArrayList<FilterValue> filterValues;

  private final FilterValue firstOpenParen =
      new FilterValue(
          1L, null, 0, ReportConstants.FilterValueType.OPERATOR.toString(), null, "(", null);
  private final FilterValue lastCloseParen =
      new FilterValue(
          2L, null, 1000, ReportConstants.FilterValueType.OPERATOR.toString(), null, ")", null);

  public AdvancedQueryBuilder(List<FilterValue> filterValues) {
    this.filterValues = new ArrayList<>(filterValues);
  }

  public String generateQueryString() {
    return String.join(
            " ",
            filterValues.stream()
                .sorted(Comparator.comparing(FilterValue::getSequenceNumber))
                .map(
                    f -> {
                      String part;

                      if (List.of("(", ")", "and", "or").contains(f.getOperator())) {
                        part = f.getOperator();
                      } else {
                        part = "COL " + f.getOperator();
                      }

                      if (f.getValueTxt() != null) {
                        part += " " + f.getValueTxt();
                      }

                      return part;
                    })
                .toList());
  }

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
    if (hasNext()) {
      LOGGER.log(
          System.Logger.Level.TRACE,
          "Advancing from FilterValue " + current + " to " + current + 1);
      current++;
    }
  }

  /**
   * Recursively construct a singular nested {@code RuleGroup} from the list of {@code FilterValue}s
   * provided.
   *
   * @throws AdvancedQueryException if any of the FilterValue set is invalid
   */
  public AdvancedQuery.RuleGroup build() throws AdvancedQueryException {
    LOGGER.log(System.Logger.Level.DEBUG, "Building query from: " + filterValues);

    // wrap the whole thing in () to make sure it's a valid rule group
    this.filterValues.addFirst(firstOpenParen);
    this.filterValues.add(lastCloseParen);

    AdvancedQuery.RuleGroup rootRuleGroup = startRuleGroup();

    if (hasNext()) {
      throw new AdvancedQueryException("Extra FilterValue left over after parsing: " + peek());
    }

    AdvancedQuery query = simplify(rootRuleGroup);
    LOGGER.log(System.Logger.Level.DEBUG, "AdvancedQuery following simplification: " + query);

    if (query instanceof AdvancedQuery.Rule) {
      return new AdvancedQuery.RuleGroup(
          UUID.randomUUID().toString(), ReportConstants.QueryCombinators.and, List.of(query));
    } else {
      return (AdvancedQuery.RuleGroup) query;
    }
  }

  private AdvancedQuery.RuleGroup startRuleGroup() throws AdvancedQueryException {
    LOGGER.log(System.Logger.Level.TRACE, "Starting rule group");

    FilterValue openParen = peek();
    if (!isOpenParen(openParen)) {
      throw new AdvancedQueryException("Expected paren to open rule group");
    }
    advance();

    AdvancedQuery firstRule;
    FilterValue next = peek();
    if (isOpenParen(next)) {
      firstRule = startRuleGroup();
    } else if (isClause(next)) {
      firstRule = buildClause(next);
      advance();
    } else if (isCloseParen(next)) { // "(" ")"
      throw new AdvancedQueryException("Invalid empty clause `()`");
    } else {
      throw new AdvancedQueryException("Expected new rule or rule group");
    }

    FilterValue combinator = peek();
    // "(" CLAUSE ")"
    if (isCloseParen(combinator)) {
      advance();
      return new AdvancedQuery.RuleGroup(
          combinator.getId().toString(), ReportConstants.QueryCombinators.and, List.of(firstRule));
    } else if (!isCombinator(combinator))
      throw new AdvancedQueryException("Expected 'and' or 'or'");
    ReportConstants.QueryCombinators firstCombinator =
        ReportConstants.QueryCombinators.valueOf(combinator.getOperator());
    advance();

    // Then build the root RuleGroup from said OPERATOR and corresponding rule
    AdvancedQuery.RuleGroup ruleGroup = buildRuleGroup(firstCombinator, firstRule);

    if (!hasNext()) throw new AdvancedQueryException("Expected closing paren");

    FilterValue closingParen = peek();
    if (!isCloseParen(closingParen)) throw new AdvancedQueryException("Expected closing paren");
    advance();

    return ruleGroup;
  }

  // ( col = 1 AND -> next token should be "(" OR CLAUSE
  @SuppressWarnings(
      "java:S3776") // Suppress "cognitive complexity" warning since this is inherently complex
  private AdvancedQuery.RuleGroup buildRuleGroup(
      ReportConstants.QueryCombinators combinator, AdvancedQuery previousRule)
      throws AdvancedQueryException {
    LOGGER.log(
        System.Logger.Level.DEBUG,
        "Building '" + combinator + "' rule group from previousRule " + previousRule);

    List<AdvancedQuery> rules = new ArrayList<>();
    rules.add(previousRule);

    AdvancedQuery.RuleGroup ruleGroup = null;

    while (hasNext()) {
      FilterValue filterValue = peek();

      if (isClause(filterValue)) {
        rules.add(buildClause(filterValue));
        advance();
        FilterValue next = peek();

        if (!isOperator(next)) throw new AdvancedQueryException("OPERATOR must follow CLAUSE");

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
        throw new AdvancedQueryException("') invalid after OPERATOR");
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

  private AdvancedQuery simplify(AdvancedQuery.RuleGroup ruleGroup) {
    LOGGER.log(System.Logger.Level.DEBUG, "Simplifying rule group: " + ruleGroup);

    //  If a RuleGroup has only one Rule
    if (ruleGroup.rules().size() == 1) {
      AdvancedQuery firstRule = ruleGroup.rules().getFirst();

      // if it's a RuleGroup
      if (firstRule instanceof AdvancedQuery.RuleGroup rg) {
        //  We can do away with the outer RuleGroup
        return simplify(rg);
      } else {
        //  Similarly if it's a Rule, except terminate the sequence
        return firstRule;
      }
    } else {
      return new AdvancedQuery.RuleGroup(
          ruleGroup.id(),
          ruleGroup.combinator(),
          ruleGroup.rules().stream()
              .map(
                  (AdvancedQuery rule) -> {
                    if (rule instanceof AdvancedQuery.Rule) {
                      return rule;
                    } else {
                      return simplify((AdvancedQuery.RuleGroup) rule);
                    }
                  })
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
    return fv.getValueType().equals(ReportConstants.FilterValueType.CLAUSE.toString());
  }

  private boolean isOperator(FilterValue fv) {
    return fv.getValueType().equals(ReportConstants.FilterValueType.OPERATOR.toString());
  }

  private boolean isCombinator(FilterValue fv) {
    return isAnd(fv) || isOr(fv);
  }
}

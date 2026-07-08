package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
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
  private final List<DataSourceColumn> columns;

  private final FilterValue firstOpenParen =
      new FilterValue(
          1L,
          null,
          0,
          ReportConstants.AdvancedFilterValueType.OPERATOR.toString(),
          null,
          "(",
          null);
  private final FilterValue lastCloseParen =
      new FilterValue(
          2L,
          null,
          1000,
          ReportConstants.AdvancedFilterValueType.OPERATOR.toString(),
          null,
          ")",
          null);

  public AdvancedQueryBuilder(List<FilterValue> filterValues, List<DataSourceColumn> columns) {
    this.filterValues =
        new ArrayList<>(
            filterValues.stream()
                .sorted(Comparator.comparing(FilterValue::getSequenceNumber))
                .toList());

    this.columns = columns;
  }

  public String generateQueryString() {
    return String.join(
        " ",
        filterValues.stream()
            .map(
                f -> {
                  if ("OPERATOR".equals(f.getValueType())) {
                    return f.getOperator();
                  } else {
                    DataSourceColumn column =
                        columns.stream()
                            .filter(c -> c.getId().equals(f.getColumnUid()))
                            .findFirst()
                            .orElse(null);
                    String columnName;

                    if (column == null) {
                      LOGGER.log(
                          System.Logger.Level.WARNING,
                          "Column not found for UID: " + f.getColumnUid());
                      columnName = "UNKNOWN_COLUMN";
                    } else {
                      columnName = column.getColumnTitle();
                    }

                    String op = f.getOperator();
                    String value = " '%s'".formatted(f.getValueTxt());

                    // translate more opaque codes and remove value for unary ops
                    if ("IN".equals(op)) {
                      op = "IS NULL";
                      value = "";
                    } else if ("NN".equals(op)) {
                      op = "IS NOT NULL";
                      value = "";
                    } else if ("BW".equals(op)) {
                      op = "BETWEEN";
                    } else if ("CO".equals(op)) {
                      op = "CONTAINS";
                    } else if ("SW".equals(op)) {
                      op = "STARTS WITH";
                    }

                    return "([%s] %s%s)".formatted(columnName, op, value);
                  }
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
    } else {
      LOGGER.log(System.Logger.Level.WARNING, "Attempted to advance past end of FilterValue list");
    }
  }

  /**
   * Recursively construct a singular nested {@code RuleGroup} from the list of {@code FilterValue}s
   * provided.
   *
   * @throws AdvancedQueryException if any of the FilterValue set is invalid
   */
  public AdvancedQuery.RuleGroup build() throws AdvancedQueryException {
    LOGGER.log(
        System.Logger.Level.DEBUG,
        "Building query from FilterValues: "
            + filterValues.stream().map(FilterValue::getId).toList());

    // wrap the whole thing in () to make sure it's a valid rule group
    this.filterValues.addFirst(firstOpenParen);
    this.filterValues.add(lastCloseParen);

    AdvancedQuery.RuleGroup rootRuleGroup = startRuleGroup();

    if (hasNext()) {
      throw new AdvancedQueryException(
          "Extra FilterValue left over after parsing: " + peek().getId());
    }

    AdvancedQuery query = simplify(rootRuleGroup);
    LOGGER.log(System.Logger.Level.DEBUG, "AdvancedQuery following simplification: " + query);

    if (query instanceof AdvancedQuery.Rule) {
      return new AdvancedQuery.RuleGroup(
          UUID.randomUUID().toString(), ReportConstants.QueryCombinators.AND, List.of(query));
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
          combinator.getId().toString(), ReportConstants.QueryCombinators.AND, List.of(firstRule));
    } else if (!isCombinator(combinator))
      throw new AdvancedQueryException("Expected 'and' or 'or'");
    ReportConstants.QueryCombinators firstCombinator =
        ReportConstants.QueryCombinators.valueOf(combinator.getOperator().toUpperCase());
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
          if (combinator.equals(
              ReportConstants.QueryCombinators.valueOf(next.getOperator().toUpperCase()))) {
            advance(); // keep on keeping on
            continue;
          }

          // and => or
          if (isOr(next) && combinator.equals(ReportConstants.QueryCombinators.AND)) {
            //  We terminate the current 'AND' group
            AdvancedQuery.RuleGroup andGroup =
                new AdvancedQuery.RuleGroup(
                    UUID.randomUUID().toString(), ReportConstants.QueryCombinators.AND, rules);
            advance();

            // And add it as a rule to the new 'OR' group, which we then build
            ruleGroup = buildRuleGroup(ReportConstants.QueryCombinators.OR, andGroup);

            // or => and
          } else if (isAnd(next) && combinator.equals(ReportConstants.QueryCombinators.OR)) {
            //  We build out the 'AND' group to completion, starting from the previous
            // FilterValue
            AdvancedQuery rule = rules.removeLast();
            advance();
            AdvancedQuery.RuleGroup andGroup =
                buildRuleGroup(ReportConstants.QueryCombinators.AND, rule);

            // And attach it to the existing list of 'OR' group rules
            rules.add(andGroup);
          }
        }

      } else if (isOpenParen(filterValue)) {
        rules.add(startRuleGroup());
      } else {
        throw new AdvancedQueryException(
            String.format("'%s' invalid after OPERATOR", filterValue.getOperator()));
      }

      if (isCloseParen(peek())) {
        if (ruleGroup == null) {
          ruleGroup = new AdvancedQuery.RuleGroup(UUID.randomUUID().toString(), combinator, rules);
        }
        return ruleGroup;
      }

      advance();
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
    return isOperator(fv)
        && fv.getOperator().equalsIgnoreCase(ReportConstants.QueryCombinators.OR.toString());
  }

  private boolean isAnd(FilterValue fv) {
    return isOperator(fv)
        && fv.getOperator().equalsIgnoreCase(ReportConstants.QueryCombinators.AND.toString());
  }

  private boolean isClause(FilterValue fv) {
    return fv.getValueType()
        .equalsIgnoreCase(ReportConstants.AdvancedFilterValueType.CLAUSE.toString());
  }

  private boolean isOperator(FilterValue fv) {
    return fv.getValueType()
        .equalsIgnoreCase(ReportConstants.AdvancedFilterValueType.OPERATOR.toString());
  }

  private boolean isCombinator(FilterValue fv) {
    return isAnd(fv) || isOr(fv);
  }
}

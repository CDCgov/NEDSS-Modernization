package gov.cdc.nbs.questionbank.pagerules;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
class PageRuleMapper implements RowMapper<Rule> {
  record Column(int ruleId, int template, int ruleFunction, int description, int sourceQuestion, int ruleExpression,
      int sourceValues, int comparator, int targetType, int targetQuestions, int sourceQuestionLabel,
      int sourceQuestionCodeSet, int targetQuestionsLabels, int totalCount) {

  }


  private final Column columns;


  PageRuleMapper() {
    this.columns = new PageRuleMapper.Column(1, 2, 3, 4,
        5, 6, 7, 8, 9, 10,
        11, 12, 13, 14);
  }

  private Long totalRowsCount = 0l;

  @Override
  public Rule mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    long ruleId = rs.getLong(columns.ruleId());
    long template = rs.getLong(columns.template());
    String function = rs.getString(columns.ruleFunction());
    String description = rs.getString(columns.description());
    String sourceQuestionIdentifier = rs.getString(columns.sourceQuestion());
    String ruleExpression = rs.getString(columns.ruleExpression());
    String sourceValues = rs.getString(columns.sourceValues());
    String comparator = rs.getString(columns.comparator());
    String targetType = rs.getString(columns.targetType());
    String sourceQuestionLabel = rs.getString(columns.sourceQuestionLabel());
    String sourceQuestionCodeSet = rs.getString(columns.sourceQuestionCodeSet());
    totalRowsCount = rs.getLong(columns.totalCount());
    Rule.RuleFunction functionEnum = Rule.RuleFunction.valueFromNullable(function);
    Rule.Comparator comparatorEnum = Rule.Comparator.valueFromNullable(comparator);
    Rule.TargetType targetTypeEnum = getTargetTypeEnum(targetType);
    Rule.SourceQuestion sourceQuestionInfo =
        new Rule.SourceQuestion(sourceQuestionIdentifier, sourceQuestionLabel, sourceQuestionCodeSet);
    boolean anySource = ruleExpression.contains("(  )");
    List<String> sourceValuesList = null;
    if (sourceValues != null)
      sourceValuesList = Arrays.asList(sourceValues.split(","));

    List<Rule.Target> targets =
        getTargets(rs.getString(columns.targetQuestions()), rs.getString(columns.targetQuestionsLabels()));

    return new Rule(ruleId, template, functionEnum, description, sourceQuestionInfo, anySource,
        sourceValuesList, comparatorEnum, targetTypeEnum, targets);
  }

  private List<Rule.Target> getTargets(String identifiers, String labels) {
    List<String> targetQuestions = identifiers != null ? Arrays.stream(identifiers.split(",")).toList() : null;

    List<String> targetQuestionsLabels = labels != null ? Arrays.stream(labels.split(",")).toList() : null;

    List<Rule.Target> targets = new ArrayList<>();
    if (targetQuestions != null) {
      int index = 0;
      while (index < targetQuestions.size()) {
        String identifier = targetQuestions.get(index);
        if (targetQuestionsLabels != null && index < targetQuestionsLabels.size()) {
          targets.add(new Rule.Target(identifier, targetQuestionsLabels.get(index)));
        } else {
          targets.add(new Rule.Target(identifier, null));
        }
        index++;
      }
    }
    return targets;
  }


  public Long getTotalRowsCount() {
    return totalRowsCount;
  }


  private Rule.TargetType getTargetTypeEnum(String value) {
    return Arrays.stream(Rule.TargetType.values())
        .filter(f -> f.toString().equalsIgnoreCase(value))
        .findFirst()
        .orElse(null);
  }

}

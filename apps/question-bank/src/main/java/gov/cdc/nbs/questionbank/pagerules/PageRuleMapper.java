package gov.cdc.nbs.questionbank.pagerules;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
class PageRuleMapper implements RowMapper<Rule> {
  record Column(
      int ruleId,
      int template,
      int ruleFunction,
      int description,
      int sourceQuestion,
      int ruleExpression,
      int sourceValues,
      int comparator,
      int targetType,
      int targetQuestions,
      int sourceQuestionLabel,
      int sourceQuestionCodeSet,
      int targetQuestionsLabels,
      int targetQuestionsTypes,
      int totalCount) {}

  private final Column columns;

  PageRuleMapper() {
    this.columns = new PageRuleMapper.Column(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
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
    Rule.RuleFunction functionEnum = getFunctionEnum(function);
    Rule.Comparator comparatorEnum = getComparatorEnum(comparator);
    Rule.TargetType targetTypeEnum = getTargetTypeEnum(targetType);
    Rule.SourceQuestion sourceQuestionInfo =
        new Rule.SourceQuestion(
            sourceQuestionIdentifier, sourceQuestionLabel, sourceQuestionCodeSet);
    boolean anySource = ruleExpression.contains("(  )");
    List<String> sourceValuesList = null;
    if (sourceValues != null) sourceValuesList = Arrays.asList(sourceValues.split(","));

    List<Rule.Target> targets =
        getTargets(
            rs.getString(columns.targetQuestions()),
            rs.getString(columns.targetQuestionsLabels()),
            rs.getString(columns.targetQuestionsTypes()));

    return new Rule(
        ruleId,
        template,
        functionEnum,
        description,
        sourceQuestionInfo,
        anySource,
        sourceValuesList,
        comparatorEnum,
        targetTypeEnum,
        targets);
  }

  private List<Rule.Target> getTargets(String identifiers, String labels, String types) {
    List<String> targetQuestions =
        identifiers != null ? Arrays.stream(identifiers.split(",")).toList() : null;
    List<String> targetQuestionsLabels =
        labels != null ? Arrays.stream(labels.split("##")).toList() : null;
    List<String> targetQuestionsTypes =
        types != null ? Arrays.stream(types.split(",")).toList() : null;

    List<Rule.Target> targets = new ArrayList<>();
    if (targetQuestions != null && targetQuestionsTypes != null) {
      int index = 0;

      while (index < targetQuestions.size()) {
        String label = null;
        String identifier = targetQuestions.get(index);
        if (targetQuestionsLabels != null && index < targetQuestionsLabels.size()) {
          label = targetQuestionsLabels.get(index);
        }
        if (label == null || label.isEmpty()) {
          label = getComponentType(targetQuestionsTypes.get(index));
        }
        targets.add(new Rule.Target(identifier, label));
        index++;
      }
    }
    return targets;
  }

  public Long getTotalRowsCount() {
    return totalRowsCount;
  }

  private Rule.RuleFunction getFunctionEnum(String value) {
    Optional<Rule.RuleFunction> functionEnum =
        Arrays.stream(Rule.RuleFunction.values())
            .filter(f -> f.getValue().equalsIgnoreCase(value))
            .findFirst();
    return functionEnum.isPresent() ? functionEnum.get() : null;
  }

  private Rule.TargetType getTargetTypeEnum(String value) {
    Optional<Rule.TargetType> targetTypeEnum =
        Arrays.stream(Rule.TargetType.values())
            .filter(f -> f.toString().equalsIgnoreCase(value))
            .findFirst();
    return targetTypeEnum.isPresent() ? targetTypeEnum.get() : null;
  }

  private Rule.Comparator getComparatorEnum(String value) {
    Optional<Rule.Comparator> comparatorEnum =
        Arrays.stream(Rule.Comparator.values())
            .filter(f -> f.getValue().equalsIgnoreCase(value))
            .findFirst();
    return comparatorEnum.isPresent() ? comparatorEnum.get() : null;
  }

  private String getComponentType(String type) {
    return switch (Integer.parseInt(type)) {
      case 1003 -> "Hyperlink";
      case 1011 -> "Subheading (for display only)";
      case 1012 -> "Line Separator";
      case 1022 -> "Table";
      case 1023 -> "Information Bar";
      case 1007 -> "Single-Select (Drop down)";
      case 1013 -> "Multi-Select (List Box)";
      case 1001 -> "CheckBox";
      case 1006 -> "Radio";
      case 1024 -> "Readonly single-select save";
      case 1025 -> "Readonly multi-select save";
      case 1027 -> "Readonly single-select no save";
      case 1028 -> "Readonly multi-select no save";
      case 1008 -> "User entered text, number, or date";
      case 1026 -> "Readonly User entered text, number, or date";
      case 1029 -> "Readonly User text, number, or date no save";
      case 1014 -> "Comments (Read only text)";
      case 1000 -> "Button";
      case 1009 -> "Multi-line user-entered text";
      case 1017 -> "Participation (Provider or Organization)";
      case 1019 -> "Multi-line Notes with User/Date Stamp";
      case 1030 -> "Readonly Participant List";
      case 1032 -> "Patient Search";
      case 1033 -> "Action Button";
      case 1034 -> "Set Values Button";
      case 1035 -> "Logic Flag";
      case 1036 -> "Original Electronic Document List";
      default -> throw new IllegalStateException("Unexpected Component Type: " + type);
    };
  }
}

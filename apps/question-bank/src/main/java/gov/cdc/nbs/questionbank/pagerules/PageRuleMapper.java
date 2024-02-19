package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
class PageRuleMapper implements RowMapper<Rule> {
  record Column(int ruleId, int template, int function, int description, int sourceQuestion,
                int ruleExpression, int sourceValues, int comparator, int targetType, int targetQuestions,
                int sourceQuestionLabel, int sourceQuestionCodeSet, int sourceQuestionId, int totalCount) {

  }


  private final Column columns;
  private final WaQuestionRepository waQuestionRepository;


  PageRuleMapper(final WaQuestionRepository waQuestionRepository) {
    this.columns = new PageRuleMapper.Column(1, 2, 3, 4,
        5, 6, 7, 8, 9, 10,
        11, 12, 13, 14);
    this.waQuestionRepository = waQuestionRepository;
  }

  private Long totalRowsCount = 0l;

  @Override
  public Rule mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    long ruleId = rs.getLong(columns.ruleId());
    long template = rs.getLong(columns.template());
    String function = rs.getString(columns.function());
    String description = rs.getString(columns.description());
    String sourceQuestionIdentifier = rs.getString(columns.sourceQuestion());
    String ruleExpression = rs.getString(columns.ruleExpression());
    String sourceValues = rs.getString(columns.sourceValues());
    String comparator = rs.getString(columns.comparator());
    String targetType = rs.getString(columns.targetType());
    String targetQuestions = rs.getString(columns.targetQuestions());
    String sourceQuestionLabel = rs.getString(columns.sourceQuestionLabel());
    String sourceQuestionCodeSet = rs.getString(columns.sourceQuestionCodeSet());
    long sourceQuestionId = rs.getLong(columns.sourceQuestionId());
    totalRowsCount = rs.getLong(columns.totalCount());

    Rule.Function functionEnum = getFunctionEnum(function);
    Rule.Comparator comparatorEnum = getComparatorEnum(comparator);
    Rule.TargetType targetTypeEnum = getTargetTypeEnum(targetType);
    Rule.SourceQuestion sourceQuestionInfo =
        new Rule.SourceQuestion(sourceQuestionId, sourceQuestionIdentifier, sourceQuestionLabel, sourceQuestionCodeSet);
    boolean anySource = ruleExpression.contains("( )");
    List<String> sourceValuesList = null;
    if (sourceValues != null)
      sourceValuesList = Arrays.asList(sourceValues.split(","));
    List<Rule.Target> targets = findLabelsByIdentifiers(Arrays.asList(targetQuestions.split(",")));
    return new Rule(ruleId, template, functionEnum, description, sourceQuestionInfo, anySource,
        sourceValuesList, comparatorEnum, targetTypeEnum, targets);
  }

  private List<Rule.Target> findLabelsByIdentifiers(List<String> targetValue) {
    return waQuestionRepository.findLabelsByIdentifiers(targetValue).stream().map(
        question -> new Rule.Target(question[0].toString(), question[1].toString())).toList();
  }

  public Long getTotalRowsCount() {
    return totalRowsCount;
  }

  private Rule.Function getFunctionEnum(String value) {
    Optional<Rule.Function> functionEnum =
        Arrays.stream(Rule.Function.values()).filter(f -> f.getValue().equalsIgnoreCase(value)).findFirst();
    return functionEnum.isPresent() ? functionEnum.get() : null;
  }

  private Rule.TargetType getTargetTypeEnum(String value) {
    Optional<Rule.TargetType> targetTypeEnum =
        Arrays.stream(Rule.TargetType.values()).filter(f -> f.toString().equalsIgnoreCase(value)).findFirst();
    return targetTypeEnum.isPresent() ? targetTypeEnum.get() : null;
  }

  private Rule.Comparator getComparatorEnum(String value) {
    Optional<Rule.Comparator> comparatorEnum =
        Arrays.stream(Rule.Comparator.values()).filter(f -> f.getValue().equalsIgnoreCase(value)).findFirst();
    return comparatorEnum.isPresent() ? comparatorEnum.get() : null;
  }
}

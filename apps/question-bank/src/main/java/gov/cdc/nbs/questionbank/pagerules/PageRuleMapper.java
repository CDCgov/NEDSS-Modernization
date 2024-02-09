package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.pagerules.response.RuleResponse;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


class PageRuleMapper implements RowMapper<RuleResponse> {
  record Column(int ruleId, int template, int function, int description, int sourceQuestion,
                int ruleExpression, int sourceValues, int comparator, int targetType, int targetQuestions,int totalCount) {
  }

  private final Column columns;

  PageRuleMapper() {
    this.columns = new Column(1, 2, 3, 4,
        5, 6, 7, 8, 9, 10,11);
  }

  @Override
  public RuleResponse mapRow(final ResultSet rs , final int rowNum) throws SQLException {
    long ruleId = rs.getLong(columns.ruleId());
    long template = rs.getLong(columns.template());
    String function = rs.getString(columns.function());
    String description = rs.getString(columns.description());
    String sourceQuestion = rs.getString(columns.sourceQuestion());
    String ruleExpression = rs.getString(columns.ruleExpression());
    String sourceValues = rs.getString(columns.sourceValues());
    String comparator = rs.getString(columns.comparator());
    String targetType = rs.getString(columns.targetType());
    String targetQuestions = rs.getString(columns.targetQuestions());
    long totalCount = rs.getLong(columns.totalCount());

    return new RuleResponse(ruleId, template, function, description, sourceQuestion, ruleExpression,
        sourceValues, comparator, targetType, targetQuestions,totalCount);

  }
}

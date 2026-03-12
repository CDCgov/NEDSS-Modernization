package gov.cdc.nbs.questionbank.page.content.subsection;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.NonNull;
import org.springframework.jdbc.core.RowMapper;

public class RdbQuestionRowMapper implements RowMapper<RdbQuestion> {

  public record Column(int identifier, int waIdentifier, int templateId, int repeatingNbr) {
    Column() {
      this(1, 2, 3, 4);
    }
  }

  private final Column columns;

  public RdbQuestionRowMapper() {
    this(new Column());
  }

  public RdbQuestionRowMapper(Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull public RdbQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
    long identifier = rs.getLong(this.columns.identifier());
    long waIdentifier = rs.getLong(this.columns.waIdentifier());
    int repeatingNbr = rs.getInt(this.columns.repeatingNbr());
    long templateId = rs.getLong(this.columns.templateId());

    return new RdbQuestion(identifier, waIdentifier, templateId, repeatingNbr);
  }
}

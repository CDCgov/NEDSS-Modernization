package gov.cdc.nbs.patient.file.demographics.race.validation;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class ExistingRaceCategoryResultSetMapper implements RowMapper<ExistingRaceCategory> {

  record Column(int identifier, int description) {
    Column() {
      this(1, 2);
    }

  }

  private final Column columns;

  ExistingRaceCategoryResultSetMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public ExistingRaceCategory mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    String identifier = rs.getString(columns.identifier());
    String description = rs.getString(columns.description());
    return new ExistingRaceCategory(identifier, description);
  }
}

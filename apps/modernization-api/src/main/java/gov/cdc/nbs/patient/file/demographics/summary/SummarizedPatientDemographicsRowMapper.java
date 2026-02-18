package gov.cdc.nbs.patient.file.demographics.summary;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class SummarizedPatientDemographicsRowMapper implements RowMapper<SummarizedPatientDemographics> {

  public record Columns(int ethnicity) {
    public Columns() {
      this(1);
    }
  }

  private final Columns columns;

  SummarizedPatientDemographicsRowMapper() {
    this(new Columns());
  }

  SummarizedPatientDemographicsRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public SummarizedPatientDemographics mapRow(final ResultSet resultSet, final int row)
      throws SQLException {
    String ethnicity = resultSet.getString(columns.ethnicity());

    return new SummarizedPatientDemographics(ethnicity);
  }
}

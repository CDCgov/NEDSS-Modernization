package gov.cdc.nbs.questionbank.valueset;

import gov.cdc.nbs.questionbank.valueset.response.ValueSetSearchResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValueSetSearchResponseMapper implements RowMapper<ValueSetSearchResponse> {

  record Column(int type, int valueSetCode, int valueSetName, int valueSetDescription, int status) {
    Column() {
      this(1, 2, 3, 4, 5);
    }
  }

  private final Column columns;

  public ValueSetSearchResponseMapper() {
    this(new Column());
  }

  ValueSetSearchResponseMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull
  public ValueSetSearchResponse mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    String type = rs.getString(columns.type());
    String valueSetCode = rs.getString(columns.valueSetCode());
    String valueSetName = rs.getString(columns.valueSetName());
    String valueSetDescription = rs.getString(columns.valueSetDescription());
    String status = rs.getString(columns.status());
    return new ValueSetSearchResponse(
        type,
        valueSetCode,
        valueSetName,
        valueSetDescription,
        status );
  }



}


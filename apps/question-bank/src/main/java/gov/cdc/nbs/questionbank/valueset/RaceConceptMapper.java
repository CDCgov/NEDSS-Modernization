package gov.cdc.nbs.questionbank.valueset;

import gov.cdc.nbs.questionbank.valueset.response.RaceConcept;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RaceConceptMapper implements RowMapper<RaceConcept> {

  record Column(int code, int codeSetName, int display, int longName,
                int codeSystem, int status, int effectiveFromTime, int effectiveToTime) {
    Column() {
      this(1, 2, 3, 4,
          5, 6, 7, 8);
    }
  }


  private final Column columns;

  public RaceConceptMapper() {
    this(new Column());
  }

  RaceConceptMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull
  public RaceConcept mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    String code = rs.getString(columns.code());
    String codeSetName = rs.getString(columns.codeSetName());
    String display = rs.getString(columns.display());
    String longName = rs.getString(columns.longName());
    String codeSystem = rs.getString(columns.codeSystem());
    String status = rs.getString(columns.status()).equals("A") ? "Active" : "Inactive";
    String effectiveFromTime = rs.getString(columns.effectiveFromTime());
    String effectiveToTime = rs.getString(columns.effectiveToTime());
    return new RaceConcept(
        code,
        codeSetName,
        display,
        longName,
        codeSystem,
        status,
        effectiveFromTime,
        effectiveToTime
    );
  }



}


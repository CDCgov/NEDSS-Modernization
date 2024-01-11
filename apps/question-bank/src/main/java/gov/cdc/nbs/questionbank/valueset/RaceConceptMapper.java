package gov.cdc.nbs.questionbank.valueset;

import gov.cdc.nbs.questionbank.valueset.response.Concept;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class RaceConceptMapper implements RowMapper<Concept> {

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
  public Concept mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    String code = rs.getString(columns.code());
    String codeSetName = rs.getString(columns.codeSetName());
    String display = rs.getString(columns.display());
    String longName = rs.getString(columns.longName());
    String codeSystem = rs.getString(columns.codeSystem());
    String status = rs.getString(columns.status()).equals("A") ? "Active" : "Inactive";
    Instant effectiveFromTime = rs.getTimestamp(columns.effectiveFromTime()) != null ?
        rs.getTimestamp(columns.effectiveFromTime()).toInstant() : null;
    Instant effectiveToTime = rs.getTimestamp(columns.effectiveToTime()) != null ?
        rs.getTimestamp(columns.effectiveFromTime()).toInstant() : null;

    return new Concept(
        code,
        codeSetName,
        display,
        longName,
        null,
        null,
        codeSystem,
        status,
        effectiveFromTime,
        effectiveToTime
    );
  }



}


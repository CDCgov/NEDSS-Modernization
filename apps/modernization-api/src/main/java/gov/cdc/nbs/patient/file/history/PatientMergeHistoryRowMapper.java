package gov.cdc.nbs.patient.file.history;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.function.Function;
import org.springframework.jdbc.core.RowMapper;

class PatientMergeHistoryRowMapper implements RowMapper<PatientMergeHistory> {

  private final Function<String, PatientIdAndName> personNameFetcher;

  PatientMergeHistoryRowMapper(Function<String, PatientIdAndName> personNameFetcher) {
    this.personNameFetcher = personNameFetcher;
  }

  @Override
  public PatientMergeHistory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    String supersededPersonId = resultSet.getString("supersededPersonId");
    String mergedByUser = resultSet.getString("mergedBy");

    Timestamp mergeTimestamp = resultSet.getTimestamp("mergeTime");
    String mergeTime = mergeTimestamp != null ? mergeTimestamp.toLocalDateTime().toString() : null;

    PatientIdAndName localIdAndName = personNameFetcher.apply(supersededPersonId);

    return new PatientMergeHistory(
        localIdAndName.personLocalId(), localIdAndName.name(), mergeTime, mergedByUser);
  }
}

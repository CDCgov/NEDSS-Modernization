package gov.cdc.nbs.patient;

import gov.cdc.nbs.data.SingleResultSetExtractor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class SQLPatientAssociationCountFinder implements PatientAssociationCountFinder {

  private static final String QUERY = """
      select
          [revision].person_parent_uid,count(*)
      from Person [revision]
      where   [revision].person_parent_uid = ?
          and [revision].record_status_cd <> 'LOG_DEL'
          and [revision].person_uid <> [revision].person_parent_uid
      group by
          [revision].person_parent_uid
      """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int RESULT_INDEX = 1;

  private final JdbcTemplate template;
  private final ResultSetExtractor<Optional<Long>> extractor;

  SQLPatientAssociationCountFinder(final JdbcTemplate template) {
    this.template = template;
    this.extractor = new SingleResultSetExtractor<>((resultSet, r) -> resultSet.getLong(RESULT_INDEX));
  }

  @Override
  public long count(long patient) {
    return template.query(
            QUERY,
            statement -> statement.setLong(PATIENT_PARAMETER, patient),
            extractor
        )
        .orElse(0L);
  }
}

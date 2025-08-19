package gov.cdc.nbs.patient.file.demographics.administrative;

import gov.cdc.nbs.patient.demographics.administrative.Administrative;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientAdministrativeInformationFinder {

  private static final String QUERY = """
      select
          [patient].as_of_date_admin,
          [patient].[description]
      from Person [patient]
      where [patient].person_uid = ?
          and [patient].cd = 'PAT'
          and [patient].as_of_date_admin is not null
      """;

  private final JdbcClient client;
  private final RowMapper<Administrative> rowMapper;

  PatientAdministrativeInformationFinder(
      final JdbcClient client
  ) {
    this.client = client;
    this.rowMapper = new PatientAdministrativeRowMapper();
  }

  Optional<Administrative> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(rowMapper)
        .optional();
  }
}

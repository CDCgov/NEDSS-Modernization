package gov.cdc.nbs.patient.profile.history;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
class PatientHistoryPreviousVersionVerifier {

  record PatientHistoryPreviousVersion(
      long patient,
      short version,
      short previous
  ){}

  private static final String QUERY = """
    select
        [patient].person_uid,
        [patient].version_ctrl_nbr,
        [history].version_ctrl_nbr
    from Person [patient]
        join Person_hist [history] on
                [history].[person_uid] = [patient].[person_uid]
            and [history].version_ctrl_nbr = (
                select max(eff_history.version_ctrl_nbr)
                from Person_hist [eff_history]
                where [eff_history].[person_uid] = [patient].[person_uid]
            )
    
    where   [patient].cd = 'PAT'
        and [patient].person_uid = ?
    """;

  private final JdbcClient client;

  PatientHistoryPreviousVersionVerifier(final JdbcClient client) {
    this.client = client;
  }

  Optional<PatientHistoryPreviousVersion> verify(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(this::map)
        .optional();
  }

  private PatientHistoryPreviousVersion map(final ResultSet resultSet, int row) throws SQLException {

    long patient = resultSet.getLong(1);
    short version = resultSet.getShort(2);
    short previous = resultSet.getShort(3);

    return new PatientHistoryPreviousVersion(
        patient,
        version,
        previous
    );
  }
}

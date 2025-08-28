package gov.cdc.nbs.patient.profile.history;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientEthnicityPreviousVersionVerifier {
  private static final String QUERY = """
      select 1 from Person_ethnic_group [current]
          join Person_ethnic_group_hist [history] on
                  [history].[person_uid] = [current].[person_uid]
      
      where [current].person_uid = ?
      """;

  private final JdbcClient client;

  PatientEthnicityPreviousVersionVerifier(final JdbcClient client) {
    this.client = client;
  }

  boolean verify(long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(Boolean.class)
        .optional()
        .orElse(false);
  }
}

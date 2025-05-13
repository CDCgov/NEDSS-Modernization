package gov.cdc.nbs.patient;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class SQLPatientAssociationCountFinder implements PatientAssociationCountFinder {

  private static final String QUERY = """
      select
          count(*)
      from Person [revision]
      where   [revision].person_parent_uid = ?
          and [revision].record_status_cd <> 'LOG_DEL'
          and [revision].person_uid <> [revision].person_parent_uid
      group by
          [revision].person_parent_uid
      """;

  private final JdbcClient client;

  SQLPatientAssociationCountFinder(final JdbcClient client) {
    this.client = client;
  }

  @Override
  public long count(long patient) {
    return client.sql(QUERY)
        .param(patient)
        .query(Long.class)
        .optional()
        .orElse(0L);
  }
}

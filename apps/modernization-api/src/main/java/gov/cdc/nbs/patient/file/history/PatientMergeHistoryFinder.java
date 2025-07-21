package gov.cdc.nbs.patient.file.history;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientMergeHistoryFinder {

  private static final String MERGE_HISTORY_QUERY = """
        SELECT
            pm.superced_person_uid AS supersededPersonId,
            u.user_id AS mergedBy,
            pm.merge_time AS mergeTime
        FROM
            person_merge pm
        LEFT JOIN
            auth_user u ON pm.merge_user_id = u.auth_user_uid
        WHERE
            pm.surviving_person_uid = ?
        ORDER BY
            pm.merge_time DESC
        """;

  private static final String PERSON_LOCAL_ID_AND_NAME_QUERY = """
        WITH
          id_settings (prefix, suffix, initial) AS (
            SELECT
              generator.UID_prefix_cd AS prefix,
              generator.UID_suffix_CD AS suffix,
              cast(config.config_value AS bigint) AS initial
            FROM
              Local_UID_generator generator
            WITH (NOLOCK),
              NBS_configuration config
            WITH (NOLOCK)
            WHERE
              generator.class_name_cd = 'PERSON'
              AND generator.type_cd = 'LOCAL'
              AND config.config_key = 'SEED_VALUE'
          )
        SELECT TOP 1
          CAST(SUBSTRING(p.local_id, LEN(id_settings.prefix) + 1, LEN(p.local_id) - LEN(id_settings.prefix) - LEN(id_settings.suffix)) AS bigint) - id_settings.initial AS personLocalId,
          CONCAT(COALESCE(pn.last_nm, '--'), ', ', COALESCE(pn.first_nm, '--')) AS name
        FROM
          id_settings, person p
          LEFT JOIN person_name pn ON pn.person_uid = p.person_uid
        WHERE
          p.person_uid = ?
        ORDER BY
          CASE
            WHEN pn.nm_use_cd = 'L' THEN 1
            ELSE 2
          END,
          pn.as_of_date DESC
        """;

  private final JdbcClient client;
  private final PatientMergeHistoryRowMapper rowMapper;

  public PatientMergeHistoryFinder(JdbcClient client) {
    this.client = client;
    this.rowMapper = new PatientMergeHistoryRowMapper(this::fetchPersonLocalIdAndName);
  }

  public List<PatientMergeHistory> find(long patientId) {
    return this.client.sql(MERGE_HISTORY_QUERY)
        .param(patientId)
        .query(rowMapper)
        .list();
  }

  private PatientIdAndName fetchPersonLocalIdAndName(String id) {
    return this.client.sql(PERSON_LOCAL_ID_AND_NAME_QUERY)
        .param(id)
        .query((rs, rowNum) -> new PatientIdAndName(
            rs.getString("personLocalId"),
            rs.getString("name")
        ))
        .optional()
        .orElse(null);
  }
}

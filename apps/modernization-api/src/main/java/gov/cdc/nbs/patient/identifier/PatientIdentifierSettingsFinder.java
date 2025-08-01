package gov.cdc.nbs.patient.identifier;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

class PatientIdentifierSettingsFinder {

  private static final String QUERY = """
      select
         [generator].UID_prefix_cd,
         [generator].UID_suffix_CD,
         [configuraiton].config_value
      from local_uid_generator [generator],
          NBS_configuration [configuraiton]
      where [generator].type_cd = 'LOCAL'
      and [generator].class_name_cd = 'PERSON'
      and [configuraiton].[config_key] = 'SEED_VALUE'
      """;

  private final JdbcClient client;

  PatientIdentifierSettingsFinder(final JdbcClient client) {
    this.client = client;
  }

  PatientIdentifierSettings find() {
    return this.client.sql(QUERY)
        .query(this::map)
        .single();
  }

  private PatientIdentifierSettings map(final ResultSet resultSet, final int row) throws SQLException {
    String prefix = resultSet.getString(1);
    String suffix = resultSet.getString(2);
    long initial = resultSet.getLong(3);

    return new PatientIdentifierSettings(prefix, initial, suffix);
  }

}

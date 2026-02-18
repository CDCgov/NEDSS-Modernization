package gov.cdc.nbs.configuration.nbs;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
class NBSConfigurationMother {

  private static final String INSERT =
      """
      merge nbs_configuration using (
          values (:key, :value)
      ) as [configuration] ([key], [value])
      on nbs_configuration.config_key = [configuration].[key]
      when matched then
          update set config_value = [configuration].[value]
      when not matched then
          insert (
              config_key,
              config_value,
              version_ctrl_nbr,
              add_user_id,
              add_time,
              last_chg_user_id,
              last_chg_time,
              status_cd,
              status_time
          )
          values (
              [configuration].[key],
              [configuration].[value],
              1,
              :user,
              getDate(),
              :user,
              getDate(),
              'A',
              getDate()
          )
      ;
      """;

  private final NamedParameterJdbcTemplate template;

  NBSConfigurationMother(final NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  void set(final String key, final String value) {
    Map<String, ? extends Serializable> parameters =
        Map.of(
            "key", key,
            "value", value,
            "user", 9999L);

    this.template.execute(INSERT, parameters, PreparedStatement::executeUpdate);
  }
}

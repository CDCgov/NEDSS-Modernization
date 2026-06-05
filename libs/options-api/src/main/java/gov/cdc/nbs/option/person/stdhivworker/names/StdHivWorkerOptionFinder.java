package gov.cdc.nbs.option.person.stdhivworker.names;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StdHivWorkerOptionFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      // The program area lists can be comma delimited
      """
      WITH std_hiv_prog as (
        SELECT CONCAT(',', hiv.config_value, ',', std.config_value, ',') as list
        FROM (SELECT config_value FROM dbo.NBS_Configuration WHERE config_key = 'HIV_PROGRAM_AREAS') hiv,
          (SELECT config_value FROM dbo.NBS_Configuration WHERE config_key = 'STD_PROGRAM_AREAS') std
      )
      SELECT DISTINCT
          root_extension_txt AS [value],
          CONCAT(first_nm, ' ', last_nm) AS [name]
        FROM
          dbo.person_name pn, dbo.entity_id ei, dbo.auth_user au, dbo.auth_user_role aur, std_hiv_prog
        WHERE
          pn.person_uid = ei.entity_uid
          AND ei.type_cd = 'QEC'
          AND au.provider_uid = pn.person_uid
          AND au.auth_user_uid = aur.auth_user_uid
          AND root_extension_txt IS NOT NULL
          AND root_extension_txt !=''
          AND CHARINDEX(CONCAT(',', aur.prog_area_cd, ','), std_hiv_prog.list) > 0
        ORDER BY [value];
      """;

  StdHivWorkerOptionFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}

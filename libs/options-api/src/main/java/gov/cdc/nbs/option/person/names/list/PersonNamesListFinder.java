package gov.cdc.nbs.option.person.names.list;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PersonNamesListFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
        SELECT
          distinct root_extension_txt AS [key],
          CONCAT(first_nm, ' ', last_nm) AS [value]
        FROM
          dbo.person_name pn, dbo.entity_id ei, dbo.auth_user au, dbo.auth_user_role aur
        WHERE
          pn.person_uid = ei.entity_uid
          AND ei.type_cd = 'QEC'
          AND au.provider_uid = pn.person_uid
          AND au.auth_user_uid = aur.auth_user_uid
          AND root_extension_txt IS NOT NULL
          AND root_extension_txt !=''
          AND aur.prog_area_cd IN (:programAreas);
      """;

  PersonNamesListFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}

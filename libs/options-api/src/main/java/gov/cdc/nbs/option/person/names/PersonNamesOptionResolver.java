package gov.cdc.nbs.option.person.names;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PersonNamesOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY =
      """
      WITH std_hiv_workers AS (
      SELECT
          DISTINCT root_extension_txt AS [key],
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
          AND aur.prog_area_cd IN (:programAreas)
      )
      SELECT [key], [value], ROW_NUMBER() OVER(ORDER BY [key]) AS [order_clause]
      FROM std_hiv_workers
      GROUP BY [key], [value];
      """;

  PersonNamesOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}

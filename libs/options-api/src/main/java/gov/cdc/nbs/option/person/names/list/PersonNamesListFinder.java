package gov.cdc.nbs.option.person.names.list;

import gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder;
import gov.cdc.nbs.configuration.nbs.Properties;
import gov.cdc.nbs.option.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class PersonNamesListFinder {

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
          AND aur.prog_area_cd IN (%s);
      """;

  private final JdbcClient client;
  private final NbsPropertiesFinder nbsPropertiesFinder;
  private final RowMapper<Option> mapper;

  PersonNamesListFinder(final JdbcClient client, final NbsPropertiesFinder nbsPropertiesFinder) {
    this.client = client;
    this.nbsPropertiesFinder = nbsPropertiesFinder;
    this.mapper = new PersonNamesListRowMapper();
  }

  private String buildProgramAreasValue() {
    Properties nbsProperties = nbsPropertiesFinder.find();
    List<String> hivProgramAreas = nbsProperties.hivProgramAreas();
    List<String> stdProgramAreas = nbsProperties.stdProgramAreas();
    List<String> stdHivProgramAreas = new ArrayList<>(hivProgramAreas);
    stdHivProgramAreas.addAll(stdProgramAreas);
    return stdHivProgramAreas.stream()
        .map(item -> "'" + item + "'")
        .collect(Collectors.joining(", "));
  }

  public Collection<Option> find() {
    String programAreasValue = buildProgramAreasValue();
    return this.client.sql(QUERY.formatted(programAreasValue)).query(this.mapper).list();
  }
}

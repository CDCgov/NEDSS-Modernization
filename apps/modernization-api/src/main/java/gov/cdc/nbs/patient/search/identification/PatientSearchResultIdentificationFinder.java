package gov.cdc.nbs.patient.search.identification;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
class PatientSearchResultIdentificationFinder {

  public static final String QUERY = """
          select distinct
            [type].code_short_desc_txt              as [type],
            [identification].root_extension_txt     as [value]
          from Person [patient]

          join [Entity_id] [identification] on
                  [identification].[entity_uid] = [patient].person_uid
              and [identification].[record_status_cd] = 'ACTIVE'

          join NBS_SRTE..Code_value_general [type] on
                  [type].code_set_nm = 'EI_TYPE_PAT'
              and [type].code = [identification].[type_cd]

            where   [patient].person_uid = ?
          and [patient].cd = 'PAT'
      """;
  private final JdbcTemplate template;

  PatientSearchResultIdentificationFinder(final JdbcTemplate template) {
    this.template = template;
  }

  Collection<PatientSearchResultIdentification> find(final long patient) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(1, patient),
        this::map
    );
  }

  private PatientSearchResultIdentification map(final ResultSet resultSet, final int row) throws SQLException {
    String type = resultSet.getString(1);
    String value = resultSet.getString(2);
    return new PatientSearchResultIdentification(type, value);
  }
}

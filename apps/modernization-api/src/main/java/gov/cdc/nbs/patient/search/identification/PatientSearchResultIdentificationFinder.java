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
          [identification].as_of_date         as [as_of],
          [type].code_short_desc_txt          as [type],
          [identification].root_extension_txt as [value]
      from [Entity_id] [identification]
          join NBS_SRTE..Code_value_general [type] on
                  [type].code_set_nm = 'EI_TYPE_PAT'
              and [type].code = [identification].[type_cd]
      
      where   [identification].[entity_uid] = ?
          and [identification].[record_status_cd] = 'ACTIVE'
          and [identification].root_extension_txt is not null
      order by
          [identification].as_of_date desc
      """;
  private static final int PATIENT_PARAMETER = 1;

  private static final int TYPE_COLUMN = 2;
  private static final int VALUE_COLUMN = 3;

  private final JdbcTemplate template;

  PatientSearchResultIdentificationFinder(final JdbcTemplate template) {
    this.template = template;
  }

  Collection<PatientSearchResultIdentification> find(final long patient) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, patient),
        this::map
    );
  }

  private PatientSearchResultIdentification map(final ResultSet resultSet, final int row) throws SQLException {
    String type = resultSet.getString(TYPE_COLUMN);
    String value = resultSet.getString(VALUE_COLUMN);
    return new PatientSearchResultIdentification(type, value);
  }
}

package gov.cdc.nbs.patient.search.indexing.identification;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchablePatientIdentificationFinder {

  private static final String QUERY = """
      select distinct
          [identification].[type_cd]          as [type],
          [identification].root_extension_txt as [value],
          [identification].[record_status_cd] as [status]
      from [Entity_id] [identification]
      where   [identification].[entity_uid]= ?
        """;
  private static final int TYPE_COLUMN = 1;
  private static final int VALUE_COLUMN = 2;
  private static final int STATUS_COLUMN = 3;
  private static final int PATIENT_PARAMETER = 1;

  private final JdbcTemplate template;
  private final RowMapper<SearchablePatient.Identification> mapper;

  public SearchablePatientIdentificationFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new SearchablePatientIdentificationRowMapper(
        new SearchablePatientIdentificationRowMapper.Column(
            TYPE_COLUMN,
            VALUE_COLUMN,
            STATUS_COLUMN
        )
    );
  }

  public List<SearchablePatient.Identification> find(final long patient) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, patient),
        this.mapper
    );
  }
}

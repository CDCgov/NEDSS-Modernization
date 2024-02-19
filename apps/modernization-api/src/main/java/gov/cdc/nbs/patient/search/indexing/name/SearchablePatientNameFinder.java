package gov.cdc.nbs.patient.search.indexing.name;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchablePatientNameFinder {

  private static final String QUERY = """
      select
          [name].nm_use_cd,
          [name].first_nm,
          [name].first_nm_sndx,
          [name].middle_nm,
          [name].last_nm,
          [name].last_nm_sndx,
          [name].nm_prefix,
          [name].nm_suffix
      from person_name [name]
      where [name].person_uid = ?
      """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int SUFFIX_COLUMN = 8;
  private static final int PREFIX_COLUMN = 7;
  private static final int LAST_SOUNDEX_COLUMN = 6;
  private static final int LAST_COLUMN = 5;
  private static final int MIDDLE_COLUMN = 4;
  private static final int FIRST_SOUNDEX_COLUMN = 3;
  private static final int FIRST_COLUMN = 2;
  private static final int USE_COLUMN = 1;

  private final JdbcTemplate template;
  private final RowMapper<SearchablePatient.Name> mapper;

  SearchablePatientNameFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new SearchablePatientNameRowMapper(
        new SearchablePatientNameRowMapper.Column(
            USE_COLUMN,
            FIRST_COLUMN,
            FIRST_SOUNDEX_COLUMN,
            MIDDLE_COLUMN,
            LAST_COLUMN,
            LAST_SOUNDEX_COLUMN,
            PREFIX_COLUMN,
            SUFFIX_COLUMN
        )
    );
  }

  public List<SearchablePatient.Name> find(final long patient) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, patient),
        this.mapper
    );
  }
}

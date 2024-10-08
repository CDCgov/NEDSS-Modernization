package gov.cdc.nbs.patient.search.indexing;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class SearchablePatientFinder {

  private static final String QUERY = """
      select
          person_uid,
          local_id,
          record_status_cd,
          birth_time,
          deceased_ind_cd,
          curr_sex_cd,
          ethnic_group_ind
      from person [patient]
      where cd = 'PAT'
      and person_uid = ?
      """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int IDENTIFIER_COLUMN = 1;
  private static final int LOCAL_COLUMN = 2;
  private static final int STATUS_COLUMN = 3;
  private static final int BIRTHDAY_COLUMN = 4;
  private static final int DECEASED_COLUMN = 5;
  private static final int GENDER_COLUMN = 6;
  private static final int ETHNICITY_COLUMN = 7;
  private static final int DOCUMENT_IDS_COLUMN = 8;
  private static final int MORBIDITY_REPORT_IDS_COLUMN = 9;
  private static final int TREATMENT_IDS_COLUMN = 10;
  private static final int VACCINATION_IDS_COLUMN = 11;

  private final JdbcTemplate template;
  private final SearchablePatientRowMapper mapper;

  SearchablePatientFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new SearchablePatientRowMapper(
        new SearchablePatientRowMapper.Column(
            IDENTIFIER_COLUMN,
            LOCAL_COLUMN,
            STATUS_COLUMN,
            BIRTHDAY_COLUMN,
            DECEASED_COLUMN,
            GENDER_COLUMN,
            ETHNICITY_COLUMN,
            DOCUMENT_IDS_COLUMN,
            MORBIDITY_REPORT_IDS_COLUMN,
            TREATMENT_IDS_COLUMN,
            VACCINATION_IDS_COLUMN));
  }

  Optional<SearchablePatient> find(long identifier) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, identifier),
        this.mapper).stream()
        .findFirst();
  }
}

package gov.cdc.nbs.patient.search.indexing.race;

import gov.cdc.nbs.patient.search.SearchablePatient;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class SearchablePatientRaceFinder {

  private static final String QUERY =
      """
      select
          [race].race_category_cd as [category],
          [race].race_cd          as [detail]
      from person_race [race]
      where [race].person_uid = ?
      """;
  private static final int CATEGORY_COLUMN = 1;
  private static final int DETAIL_COLUMN = 2;
  private static final int PATIENT_PARAMETER = 1;

  private final JdbcTemplate template;
  private final RowMapper<SearchablePatient.Race> mapper;

  public SearchablePatientRaceFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper =
        new SearchablePatientRaceRowMapper(
            new SearchablePatientRaceRowMapper.Column(CATEGORY_COLUMN, DETAIL_COLUMN));
  }

  public List<SearchablePatient.Race> find(final long patient) {
    return this.template.query(
        QUERY, statement -> statement.setLong(PATIENT_PARAMETER, patient), this.mapper);
  }
}

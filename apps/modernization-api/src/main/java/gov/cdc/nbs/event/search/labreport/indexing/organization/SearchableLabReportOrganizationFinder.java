package gov.cdc.nbs.event.search.labreport.indexing.organization;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchableLabReportOrganizationFinder {

  private static final String QUERY =
      """
      select
          [organization].[organization_uid],
          [participation].type_cd,
          [participation].subject_class_cd,
          [organization].[display_nm]
      from [Participation] [participation]

          join Organization [organization] on
                  [organization].organization_uid = [participation].subject_entity_uid

      where   [participation].record_status_cd = 'ACTIVE'
          and [subject_class_cd] = 'ORG'
          and [participation].[act_class_cd] = 'OBS'
          and [participation].[act_uid] = ?
      """;

  private static final int LAB_REPORT_PARAMETER = 1;
  private static final int IDENTIFIER_COLUMN = 1;
  private static final int TYPE_COLUMN = 2;
  private static final int SUBJECT_TYPE_COLUMN = 3;
  private static final int NAME_COLUMN = 4;

  private final JdbcTemplate template;
  private final SearchableLabReportOrganizationRowMapper mapper;

  public SearchableLabReportOrganizationFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper =
        new SearchableLabReportOrganizationRowMapper(
            new SearchableLabReportOrganizationRowMapper.Column(
                IDENTIFIER_COLUMN, TYPE_COLUMN, SUBJECT_TYPE_COLUMN, NAME_COLUMN));
  }

  public List<SearchableLabReport.Organization> find(final long lab) {
    return this.template.query(
        QUERY, statement -> statement.setLong(LAB_REPORT_PARAMETER, lab), this.mapper);
  }
}

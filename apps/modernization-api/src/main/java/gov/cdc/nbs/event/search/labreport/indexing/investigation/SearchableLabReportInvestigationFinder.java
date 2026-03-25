package gov.cdc.nbs.event.search.labreport.indexing.investigation;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchableLabReportInvestigationFinder {

  private static final String QUERY =
      """
      select
          [investigation].[local_id],
          [condition].condition_short_nm
      from Act_relationship [relation]
          join Public_health_case [investigation] on
                  [investigation].[public_health_case_uid] = [relation].target_act_uid
            join nbs_srte..Condition_code [condition] on
                          [condition].condition_cd = [investigation].cd

      where   [relation].[source_act_uid] = ?
          and [relation].[source_class_cd] = 'OBS'
          and [relation].[target_class_cd] = 'CASE'
      """;

  private static final int LAB_REPORT_PARAMETER = 1;
  private static final int LOCAL_COLUMN = 1;
  private static final int CONDITION_COLUMN = 2;

  private final JdbcTemplate template;
  private final SearchableLabReportInvestigationRowMapper mapper;

  public SearchableLabReportInvestigationFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper =
        new SearchableLabReportInvestigationRowMapper(
            new SearchableLabReportInvestigationRowMapper.Column(LOCAL_COLUMN, CONDITION_COLUMN));
  }

  public List<SearchableLabReport.Investigation> find(final long lab) {
    return this.template.query(
        QUERY, statement -> statement.setLong(LAB_REPORT_PARAMETER, lab), this.mapper);
  }
}

package gov.cdc.nbs.event.search.labreport.indexing.test;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchableLabReportTestFinder {

  private static final String QUERY =
      """
      select
          [lab_test].lab_test_desc_txt        as [Lab Test],
          [coded_result].lab_result_desc_txt  as [coded_result],
          [resulted].[alt_cd]
      from  Act_relationship [lab_result_components]

          join observation [resulted] on
                  [resulted].observation_uid = [lab_result_components].[source_act_uid]
              and [resulted].obs_domain_cd_st_1 = 'Result'

          left join NBS_SRTE..Lab_test [lab_test] on
                  [lab_test].lab_test_cd = [resulted].cd

          left join [Obs_value_coded] [coded] on
                  [coded].[observation_uid] = [resulted].[observation_uid]

          left join NBS_SRTE..Lab_result [coded_result] on
                  [coded_result].[lab_result_cd] = [coded].code

      where [lab_result_components].target_act_uid = ?
              and [lab_result_components].type_cd = 'COMP'
              and [lab_result_components].source_class_cd = 'OBS'
              and [lab_result_components].target_class_cd = 'OBS'
      """;
  private static final int NAME_COLUMN = 1;
  private static final int RESULT_COLUMN = 2;
  private static final int ALTERNATIVE_COLUMN = 3;

  private static final int LAB_REPORT_PARAMETER = 1;

  private final JdbcTemplate template;
  private final SearchableLabReportLabTestRowMapper mapper;

  public SearchableLabReportTestFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper =
        new SearchableLabReportLabTestRowMapper(
            new SearchableLabReportLabTestRowMapper.Column(
                NAME_COLUMN, RESULT_COLUMN, ALTERNATIVE_COLUMN));
  }

  public List<SearchableLabReport.LabTest> find(final long lab) {
    return this.template.query(
        QUERY, statement -> statement.setLong(LAB_REPORT_PARAMETER, lab), this.mapper);
  }
}

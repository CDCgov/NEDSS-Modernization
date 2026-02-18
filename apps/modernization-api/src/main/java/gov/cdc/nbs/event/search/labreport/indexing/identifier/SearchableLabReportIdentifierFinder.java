package gov.cdc.nbs.event.search.labreport.indexing.identifier;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchableLabReportIdentifierFinder {

  private static final String QUERY =
      """
      select
          [identifier].type_cd,
          [identifier].type_desc_txt,
          [identifier].[root_extension_txt]
      from [Act_id] [identifier]
      where   [identifier].[act_uid] = ?
          and [identifier].type_cd = 'FN'
      """;

  private static final int LAB_REPORT_PARAMETER = 1;
  private static final int TYPE_COLUMN = 1;
  private static final int DESCRIPTION_COLUMN = 2;
  private static final int VALUE_COLUMN = 3;

  private final JdbcTemplate template;
  private final SearchableLabReportIdentifierRowMapper mapper;

  public SearchableLabReportIdentifierFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper =
        new SearchableLabReportIdentifierRowMapper(
            new SearchableLabReportIdentifierRowMapper.Column(
                TYPE_COLUMN, DESCRIPTION_COLUMN, VALUE_COLUMN));
  }

  public List<SearchableLabReport.Identifier> find(final long lab) {
    return this.template.query(
        QUERY, statement -> statement.setLong(LAB_REPORT_PARAMETER, lab), this.mapper);
  }
}

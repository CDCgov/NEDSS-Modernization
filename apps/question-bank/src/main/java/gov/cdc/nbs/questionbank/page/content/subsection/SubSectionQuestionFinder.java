package gov.cdc.nbs.questionbank.page.content.subsection;

import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
class SubSectionQuestionFinder {

  private static final String QUERY_STRING =
      """
            select
                [component].wa_rdb_metadata_uid     as [identifier],
                [component].wa_ui_metadata_uid      as [waIdentifier],
                [component].wa_template_uid         as [templateId],
                [component].block_pivot_nbr        as [repeatingNbr]

            from WA_RDB_metadata [component]

            where  [component].wa_template_uid = ?
            """;

  private final JdbcTemplate template;
  private final RowMapper<RdbQuestion> mapper;
  private static final int PAGE_PARAMETER = 1;

  SubSectionQuestionFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new RdbQuestionRowMapper();
  }

  Collection<RdbQuestion> resolve(final long id) {
    return this.template.query(
        QUERY_STRING, statement -> statement.setLong(PAGE_PARAMETER, id), this.mapper);
  }
}

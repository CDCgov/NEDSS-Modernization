package gov.cdc.nbs.questionbank.page.detail;

import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
class RuleFinder {

  private static final String QUERY =
      """
      select
           [rule].wa_rule_metadata_uid         as [identifier],
           [rule].wa_template_uid              as [page],
           [rule].[logic]                      as [logic],
           [rule].source_values                as [source_values],
           [rule].rule_cd                      as [function],
           [rule].[source_question_identifier] as [source_question],
           [rule].[target_question_identifier] as [target_question]
       from WA_rule_metadata [rule]
       where [rule].[wa_template_uid] = ?
             """;
  private static final int IDENTIFIER_PARAMETER = 1;

  private final JdbcTemplate template;
  private final RowMapper<PagesRule> mapper;

  RuleFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new RuleRowMapper();
  }

  Collection<PagesRule> find(final long identifier) {
    return this.template.query(
        QUERY, statement -> statement.setLong(IDENTIFIER_PARAMETER, identifier), this.mapper);
  }
}

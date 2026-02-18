package gov.cdc.nbs.questionbank.option.page.name;

import gov.cdc.nbs.questionbank.option.PageBuilderOption;
import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
class PageNameOptionFinder {

  private static final String QUERY =
      """
      select
          template_nm
      from wa_template [page]
      where template_type in ('Draft', 'Published')
      order by
          template_nm
            """;

  private final JdbcTemplate template;
  private final RowMapper<PageBuilderOption> mapper;

  PageNameOptionFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new PageNameRowMapper();
  }

  Collection<PageBuilderOption> all() {
    return this.template.query(QUERY, this.mapper);
  }
}

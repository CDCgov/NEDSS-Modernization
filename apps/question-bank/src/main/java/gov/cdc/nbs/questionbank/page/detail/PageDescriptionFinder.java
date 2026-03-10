package gov.cdc.nbs.questionbank.page.detail;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
class PageDescriptionFinder {

  private static final String QUERY =
      """
      select
          [template].wa_template_uid  as [identifier],
          [template].template_nm            as [name],
          [template].template_type          as [status],
          [template].desc_txt               as [description],
          [template].publish_version_nbr    as [versionNumber]
      from WA_template [template]
      where [template].wa_template_uid = ?
      """;
  private static final int IDENTIFIER_PARAMETER = 1;

  private final JdbcTemplate template;
  private final RowMapper<PageDescription> mapper;

  PageDescriptionFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new PageDescriptionRowMapper();
  }

  Optional<PageDescription> find(final long identifier) {
    return this.template
        .query(QUERY, statement -> statement.setLong(IDENTIFIER_PARAMETER, identifier), this.mapper)
        .stream()
        .findFirst();
  }
}

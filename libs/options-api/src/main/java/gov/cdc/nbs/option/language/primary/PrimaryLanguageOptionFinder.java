package gov.cdc.nbs.option.language.primary;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class PrimaryLanguageOptionFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select
          code                                as [value],
          code_desc_txt                       as [name],
          row_number() over (order by [code]) as [order]
      from NBS_SRTE..Language_code
      where status_cd = 'A'
      order by
        code_desc_txt
      """;

  PrimaryLanguageOptionFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}

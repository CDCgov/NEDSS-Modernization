package gov.cdc.nbs.option.language.primary.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class PrimaryLanguageOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY =
      """
      select
          code                                as [value],
          code_desc_txt                       as [name],
          row_number() over (order by [code]) as [order]
      from NBS_SRTE..Language_code
      where   status_cd = 'A'
          and code_desc_txt like :criteria
      order by
          code_desc_txt
      offset 0 rows
      fetch next :limit rows only
      """;

  PrimaryLanguageOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}

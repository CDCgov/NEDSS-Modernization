package gov.cdc.nbs.option.providers.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class ProviderOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY =
      """
      with [user]([value], [name], [quickCode]) AS (
          select
              [provider].person_uid,
              ISNULL([provider].first_nm + ' ', '') + ISNULL([provider].last_nm, '')
                + IIF(
                    [quick_code].root_extension_txt is null or [quick_code].root_extension_txt='',
                    '',
                    ' [' + [quick_code].root_extension_txt + ']'
              ),
              root_extension_txt
          from Person [provider]
          left join Entity_id [quick_code] on
                [quick_code].entity_uid = [provider].person_uid
            and [quick_code].type_cd='QEC'
          where   [provider].cd='PRV'
              and ([provider].electronic_ind is null or [provider].electronic_ind <> 'Y')
      )
      select
          [value],
          [name],
          row_number() over( order by [name])
      from [user]
      where [quickCode]=:quickCode or [name] like :criteria or [name] like :prefixCriteria
      order by
          [name]

      offset 0 rows
      fetch next :limit rows only
      """;

  ProviderOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}

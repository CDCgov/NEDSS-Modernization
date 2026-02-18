package gov.cdc.nbs.option.users.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY =
      """
      with [user]([value], [name]) as (
          select
              nedss_entry_id,
              user_first_nm
                  +   case
                          when user_last_nm is null then ''
                          else ' ' + user_last_nm
                      end
          from Auth_user
      )
      select
          [value],
          [name],
          row_number() over( order by [name])
      from [user]
      where [name] like :criteria

      order by
          [name]

      offset 0 rows
      fetch next :limit rows only
      """;

  public UserOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}

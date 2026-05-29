package gov.cdc.nbs.option.users.list;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserOptionFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select
        nedss_entry_id as [value],
        user_first_nm
            +   case
                    when user_last_nm is null then ''
                        else ' ' + user_last_nm
                    end as [name],
        0 as [order]
        from Auth_user
      order by
          [name]
      """;

  public UserOptionFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}

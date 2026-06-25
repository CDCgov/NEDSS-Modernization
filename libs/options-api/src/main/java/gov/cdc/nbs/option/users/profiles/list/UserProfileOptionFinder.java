package gov.cdc.nbs.option.users.profiles.list;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserProfileOptionFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select
        NEDSS_ENTRY_ID as [value],
        FIRST_NM
            +   case
                    when LAST_NM is null then ''
                        else ' ' + LAST_NM
                    end as [name]
        from USER_PROFILE
      order by
          [name]
      """;

  public UserProfileOptionFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}

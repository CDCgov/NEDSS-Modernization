package gov.cdc.nbs.codes.user;

import gov.cdc.nbs.data.pagination.WindowedPagedResultSetHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class UserListItemFinder {

  private static final int TOTAL_COLUMN = 1;
  private static final String QUERY = """
      select distinct
          count (*) over () as total,
          [user].[nedss_entry_id],
          [user].user_id,
          [user].[user_first_nm],
          [user].[user_last_nm]
      from Auth_user [user]
          join auth_user_role [role] on
              [role].auth_user_uid = [user].auth_user_uid
          and [role].prog_area_cd in (
              select distinct
                  [requester_role].prog_area_cd
              from [auth_user] [requester]
              join [auth_user_role] [requester_role] on
                      [requester_role].auth_user_uid=[requester].auth_user_uid
              where [requester].nedss_entry_id = :requester
          )
      order by
          [user].[user_first_nm],
          [user].[user_last_nm]
      offset :offset rows
      fetch next :limit rows only
      """;
  private static final int NEDSS_ENTRY_ID_COLUMN = 2;
  private static final int USER_ID_COLUMN = 3;
  private static final int USER_FIRST_NM_COLUMN = 4;
  private static final int USER_LAST_NM_COLUMN = 5;

  private final NamedParameterJdbcTemplate template;
  private final RowMapper<UserListItem> mapper;

  UserListItemFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
    this.mapper = new UserListItemRowMapper(
        new UserListItemRowMapper.Column(
            NEDSS_ENTRY_ID_COLUMN,
            USER_ID_COLUMN,
            USER_FIRST_NM_COLUMN,
            USER_LAST_NM_COLUMN
        )
    );
  }

  /**
   * Finds users that share a program area with the requesting user based on the NBS 6.X class DbAuthDAOImpl.java
   * "getSecureUserDTListBasedOnProgramArea"
   *
   * @param requester The unique identifier of the user requesting the list.
   * @param pageable  The {@link Pageable} that defines which page of users to return.
   * @return A {@link Page} of {@link UserListItem} items that share a program area with the requester.
   */
  Page<UserListItem> find(final long requester, final Pageable pageable) {
    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of(
            "requester", requester,
            "offset", pageable.getOffset(),
            "limit", pageable.getPageSize()
        )
    );

    WindowedPagedResultSetHandler<UserListItem> handler =
        new WindowedPagedResultSetHandler<>(TOTAL_COLUMN, this.mapper);

    this.template.query(QUERY, parameters, handler);

    return handler.asPaged(pageable);
  }
}

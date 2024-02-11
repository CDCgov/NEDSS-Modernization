package gov.cdc.nbs.codes.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class UserListItemRowMapper implements RowMapper<UserListItem> {

  record Column(
      int nedssEntryId,
      int userId,
      int userFirstNm,
      int userLastNm
  ){}

  private final Column columns;

  UserListItemRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public UserListItem mapRow(
      final ResultSet resultSet,
      final int rowNum
  ) throws SQLException {

    long nedssEntryId = resultSet.getLong(columns.nedssEntryId());
    String userId = resultSet.getString(columns.userId());
    String userFirstNm = resultSet.getString(columns.userFirstNm());
    String userLastNm = resultSet.getString(columns.userLastNm());

    return new UserListItem(
        nedssEntryId,
        userId,
        userFirstNm,
        userLastNm
    );
  }
}

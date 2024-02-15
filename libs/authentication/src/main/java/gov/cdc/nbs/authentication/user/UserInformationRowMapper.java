package gov.cdc.nbs.authentication.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class UserInformationRowMapper implements RowMapper<UserInformation> {

  record Column(
      int identifier,
      int first,
      int last,
      int username,
      int enabled
  ) {
  }


  private final Column columns;

  UserInformationRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public UserInformation mapRow(
      final ResultSet resultSet,
      final int rowNum
  ) throws SQLException {

    long identifier = resultSet.getLong(this.columns.identifier());
    String first = resultSet.getString(this.columns.first());
    String last = resultSet.getString(this.columns.last());
    String username = resultSet.getString(this.columns.username());
    boolean enabled = resultSet.getBoolean(this.columns.enabled());

    return new UserInformation(
        identifier,
        first,
        last,
        username,
        enabled
    );
  }

}

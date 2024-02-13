package gov.cdc.nbs.codes.user;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserListItemRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {


    UserListItemRowMapper.Column column = new UserListItemRowMapper.Column(1, 2, 3, 4);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(column.nedssEntryId())).thenReturn(97L);
    when(resultSet.getString(column.userId())).thenReturn("user-id-value");
    when(resultSet.getString(column.userFirstNm())).thenReturn("first-name-value");
    when(resultSet.getString(column.userLastNm())).thenReturn("last-name-value");


    UserListItemRowMapper mapper = new UserListItemRowMapper(column);

    UserListItem mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.nedssEntryId()).isEqualTo(97L);
    assertThat(mapped.userId()).isEqualTo("user-id-value");
    assertThat(mapped.userFirstNm()).isEqualTo("first-name-value");
    assertThat(mapped.userLastNm()).isEqualTo("last-name-value");
  }
}

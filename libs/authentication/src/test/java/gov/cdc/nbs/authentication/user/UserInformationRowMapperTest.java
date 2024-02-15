package gov.cdc.nbs.authentication.user;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserInformationRowMapperTest {


  @Test
  void should_map_from_result_set() throws SQLException {


    UserInformationRowMapper.Column column = new UserInformationRowMapper.Column(2, 3, 5, 7, 11);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(column.identifier())).thenReturn(337L);
    when(resultSet.getString(column.first())).thenReturn("first-value");
    when(resultSet.getString(column.last())).thenReturn("last-value");
    when(resultSet.getString(column.username())).thenReturn("username-value");
    when(resultSet.getBoolean(column.enabled())).thenReturn(true);

    UserInformationRowMapper mapper = new UserInformationRowMapper(column);

    UserInformation mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.identifier()).isEqualTo(337L);
    assertThat(mapped.first()).isEqualTo("first-value");
    assertThat(mapped.last()).isEqualTo("last-value");
    assertThat(mapped.username()).isEqualTo("username-value");
    assertThat(mapped.enabled()).isTrue();



  }
}

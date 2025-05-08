package gov.cdc.nbs.data;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SingleResultSetExtractor<T> implements ResultSetExtractor<Optional<T>> {

  private final RowMapper<T> rowMapper;

  public SingleResultSetExtractor(final RowMapper<T> rowMapper) {
    this.rowMapper = rowMapper;
  }

  @Override
  public Optional<T> extractData(final ResultSet resultSet) throws SQLException, DataAccessException {

    if(resultSet.next()) {
      T mapped = rowMapper.mapRow(resultSet, 1);
      return Optional.ofNullable(mapped );
    }

    return Optional.empty();
  }
}

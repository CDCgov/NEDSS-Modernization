package gov.cdc.nbs.sql;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.BiFunction;

public class MergingResultSetExtractor<V> implements ResultSetExtractor<Optional<V>> {


  private final RowMapper<V> valueMapper;
  private final BiFunction<V, V, V> mergeFunction;

  public MergingResultSetExtractor(
      final RowMapper<V> valueMapper,
      final BiFunction<V, V, V> mergeFunction
  ) {

    this.valueMapper = valueMapper;
    this.mergeFunction = mergeFunction;
  }

  @Override
  public Optional<V> extractData(final ResultSet resultSet) throws SQLException, DataAccessException {

    V result = null;

    for (int row = 0; resultSet.next(); row++) {
      V current = valueMapper.mapRow(resultSet, row);

      if (result == null) {
        result = current;
      } else {
        result = mergeFunction.apply(result, current);
      }

    }

    return Optional.ofNullable(result);
  }
}

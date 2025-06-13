package gov.cdc.nbs.sql;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.BinaryOperator;

/**
 * A {@link ResultSetExtractor} that takes a {@link ResultSet} of zero or more rows and returns an {@link Optional}.
 * When there is more than one row present a merge function is used to combine the values into one instance.  An empty
 * {@code Optional} is returned when the {@code ResultSet} is empty or the mapper resolves a {@code null} value.
 *
 *
 * @param <V> They type of the object being merged.
 */
public class MergingResultSetExtractor<V> implements ResultSetExtractor<Optional<V>> {


  private final RowMapper<V> mapper;
  private final BinaryOperator<V> merger;

  /**
   * @param mapper The {@link RowMapper} used to map a {@link ResultSet} to a {@code V}.
   * @param merger The function used to merger two instances of {@code V}.
   */
  public MergingResultSetExtractor(
      final RowMapper<V> mapper,
      final BinaryOperator<V> merger
  ) {

    this.mapper = mapper;
    this.merger = merger;
  }

  @Override
  public Optional<V> extractData(final ResultSet resultSet) throws SQLException, DataAccessException {

    V result = null;

    for (int row = 0; resultSet.next(); row++) {
      V current = mapper.mapRow(resultSet, row);

      if (result == null) {
        result = current;
      } else {
        result = merger.apply(result, current);
      }

    }

    return Optional.ofNullable(result);
  }
}

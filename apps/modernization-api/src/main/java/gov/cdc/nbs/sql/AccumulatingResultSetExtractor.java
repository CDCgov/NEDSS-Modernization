package gov.cdc.nbs.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

import jakarta.validation.constraints.Null;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

/**
 * A {@link ResultSetExtractor} that takes a {@link ResultSet} of zero or more rows and accumulates
 * the rows into a {@link Collection} of values. Each value within the collection can be created
 * from one or more rows. When two values evaluate to the same key they are merged.
 *
 * @param <K> The type of the key used to identify multiple rows representing the same object.
 * @param <V> The type of the objects represented in the {@code ResultSet}.
 */
public class AccumulatingResultSetExtractor<K, V> implements ResultSetExtractor<Collection<V>> {

  private final RowMapper<K> keyMapper;
  private final RowMapper<V> valueMapper;
  private final BinaryOperator<V> merger;

  /**
   * @param keyMapper The {@link RowMapper} used to map a {@link ResultSet} to a {@code K}.
   * @param valueMapper The {@link RowMapper} used to map a {@link ResultSet} to a {@code V}.
   * @param merger The function used to merger two instances of {@code V} that map to the same
   *     {@code K}.
   */
  public AccumulatingResultSetExtractor(
      final RowMapper<K> keyMapper,
      final RowMapper<V> valueMapper,
      final BinaryOperator<V> merger) {
    this.keyMapper = keyMapper;
    this.valueMapper = valueMapper;
    this.merger = merger;
  }

  //  Suppressing java:S2638 (Method overrides should not change contracts), which was being
  //  thrown because of "the incompatibility of the annotation @Nullable to honor @NonNullApi
  //  at package level of the overridden method".  The parent method on `ResultSetExtractor` is
  //  annotated with `@Nullable` just like this method, so it's unclear where the purported
  //  incompatibility is stemming from.
  @SuppressWarnings("java:S2638")
  @Override
  @Nullable
  public Collection<V> extractData(final ResultSet resultSet)
      throws SQLException, DataAccessException {

    Map<K, V> map = new LinkedHashMap<>();

    for (int row = 0; resultSet.next(); row++) {
      K key = keyMapper.mapRow(resultSet, row);
      V value = valueMapper.mapRow(resultSet, row);

      if (key != null && value != null) {
        map.compute(key, (k, existing) -> existing == null ? value : merger.apply(existing, value));
      }
    }

    return map.values();
  }
}

package gov.cdc.nbs.sql;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class MultiMapResultSetExtractor<K, V> implements ResultSetExtractor<Multimap<K, V>> {

  private final RowMapper<K> keyMapper;
  private final RowMapper<V> valueMapper;

  public MultiMapResultSetExtractor(final RowMapper<K> keyMapper, final RowMapper<V> valueMapper) {
    this.keyMapper = keyMapper;
    this.valueMapper = valueMapper;
  }

  @Override
  public Multimap<K, V> extractData(final ResultSet resultSet)
      throws SQLException, DataAccessException {

    ArrayListMultimap<K, V> multimap = ArrayListMultimap.create();

    for (int row = 0; resultSet.next(); row++) {
      K key = keyMapper.mapRow(resultSet, row);
      V value = valueMapper.mapRow(resultSet, row);

      if (key != null && value != null) {
        multimap.put(key, value);
      }
    }

    return multimap;
  }
}
